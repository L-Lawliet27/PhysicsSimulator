package simulator.launcher;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.json.JSONObject;

import simulator.control.Controller;
import simulator.control.StateComparator;
import simulator.factories.*;
import simulator.model.Body;
import simulator.model.ForceLaws;
import simulator.model.PhysicsSimulator;
import simulator.view.MainWindow;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {

	// default values for some parameters
	//
	private final static Double _dtimeDefaultValue = 2500.0;
	private final static String _forceLawsDefaultValue = "nlug";
	private final static String _stateComparatorDefaultValue = "epseq";
	private final static Integer _stepsDefaultValue = 150;
	private final static String _modeDefaultValue = "batch";

	// some attributes to stores values corresponding to command-line parameters
	//
	private static Double _dtime = null;
	private static String _inFile = null;
	private static String _outFile = null;
	private static Integer _steps = null;
	private static String _eoFile = null;
	private static JSONObject _forceLawsInfo = null;
	private static JSONObject _stateComparatorInfo = null;
	private static String _mode = null;

	// factories
	private static Factory<Body> _bodyFactory;
	private static Factory<ForceLaws> _forceLawsFactory;
	private static Factory<StateComparator> _stateComparatorFactory;

	private static void init() {
		// initialize the bodies factory
		ArrayList<Builder<Body>> bodyBuilders = new ArrayList<>();
		bodyBuilders.add(new BasicBodyBuilder<>());
		bodyBuilders.add(new MassLosingBodyBuilder<>());
		_bodyFactory = new BuilderBasedFactory<>(bodyBuilders);

		// initialize the force laws factory
		ArrayList<Builder<ForceLaws>> forceBuilders = new ArrayList<>();
		forceBuilders.add(new NewtonUniversalGravitationBuilder<>());
		forceBuilders.add(new MovingTowardsFixedPointBuilder<>());
		forceBuilders.add(new NoForceBuilder<>());
		_forceLawsFactory = new BuilderBasedFactory<>(forceBuilders);

		//  initialize the state comparator
		ArrayList<Builder<StateComparator>> stateBuilders = new ArrayList<>();
		stateBuilders.add(new MassEqualStatesBuilder<>());
		stateBuilders.add(new EpsilonEqualStatesBuilder<>());
		_stateComparatorFactory = new BuilderBasedFactory<>(stateBuilders);


	}

	private static void parseArgs(String[] args) {

		// define the valid command line options
		//
		Options cmdLineOptions = buildOptions();

		// parse the command line as provided in args
		//
		CommandLineParser parser = new DefaultParser();
		try {
			CommandLine line = parser.parse(cmdLineOptions, args);

			parseHelpOption(line, cmdLineOptions);
			parseInFileOption(line);

			// support of -o, -eo, and -s-------
			parseOutFileOption(line);
			parseExpectedOutputFileOption(line);
			parseStepsOption(line);
			//-----------------------------------

			// Mode -----------------------------
			parseModeOption(line);
			//-----------------------------------

			parseDeltaTimeOption(line);
			parseForceLawsOption(line);
			parseStateComparatorOption(line);

			// if there are some remaining arguments, then something wrong is
			// provided in the command line!
			//
			String[] remaining = line.getArgs();
			if (remaining.length > 0) {
				StringBuilder error = new StringBuilder("Illegal arguments:");
				for (String o : remaining)
					error.append(" ").append(o);
				throw new ParseException(error.toString());
			}

		} catch (ParseException e) {
			System.err.println(e.getLocalizedMessage());
			System.exit(1);
		}

	}

	private static Options buildOptions() {
		Options cmdLineOptions = new Options();

		// help
		cmdLineOptions.addOption(Option.builder("h").longOpt("help").desc("Print this message.").build());

		// input file
		cmdLineOptions.addOption(Option.builder("i").longOpt("input").hasArg().desc("Bodies JSON input file.").build());


		// mode
		cmdLineOptions.addOption(Option.builder("m").longOpt("mode").hasArg().desc("Execution Mode. Possible values: ’batch’\n" +
				"(Batch mode), ’gui’ (Graphical User Interface mode). Default value: ’batch’.").build());


		// support for -o, -eo, and -s ------------------------------------------------------------------------------------------------------------
		cmdLineOptions.addOption(Option.builder("o").longOpt("output").hasArg().desc("Output file, where output is written. If not provided" +
				"it is written in commandline").build());
		cmdLineOptions.addOption(Option.builder("eo").longOpt("expected-output").hasArg().desc("The expected output file. If not provided\n" +
				"no comparison is applied").build());
		cmdLineOptions.addOption(Option.builder("s").longOpt("steps").hasArg().desc("An integer representing the number of\n" +
				"simulation steps. Default value: 150.").build());
		//------------------------------------------------------------------------------------------------------------------------------------------


		// delta-time
		cmdLineOptions.addOption(Option.builder("dt").longOpt("delta-time").hasArg()
				.desc("A double representing actual time, in seconds, per simulation step. Default value: "
						+ _dtimeDefaultValue + ".")
				.build());

		// force laws
		cmdLineOptions.addOption(Option.builder("fl").longOpt("force-laws").hasArg()
				.desc("Force laws to be used in the simulator. Possible values: "
						+ factoryPossibleValues(_forceLawsFactory) + ". Default value: '" + _forceLawsDefaultValue
						+ "'.")
				.build());

		// gravity laws
		cmdLineOptions.addOption(Option.builder("cmp").longOpt("comparator").hasArg()
				.desc("State comparator to be used when comparing states. Possible values: "
						+ factoryPossibleValues(_stateComparatorFactory) + ". Default value: '"
						+ _stateComparatorDefaultValue + "'.")
				.build());

		return cmdLineOptions;
	}

	public static String factoryPossibleValues(Factory<?> factory) {
		if (factory == null)
			return "No values found (the factory is null)";

		StringBuilder s = new StringBuilder();

		for (JSONObject fe : factory.getInfo()) {
			if (s.length() > 0) {
				s.append(", ");
			}
			s.append("'").append(fe.getString("type")).append("' (").append(fe.getString("desc")).append(")");
		}

		s.append(". You can provide the 'data' json attaching :{...} to the tag, but without spaces.");
		return s.toString();
	}

	private static void parseHelpOption(CommandLine line, Options cmdLineOptions) {
		if (line.hasOption("h")) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp(Main.class.getCanonicalName(), cmdLineOptions, true);
			System.exit(0);
		}
	}

	private static void parseInFileOption(CommandLine line) {
		_inFile = line.getOptionValue("i");
	}

	private static void parseDeltaTimeOption(CommandLine line) throws ParseException {
		String dt = line.getOptionValue("dt", _dtimeDefaultValue.toString());
		try {
			_dtime = Double.parseDouble(dt);
			assert (_dtime > 0);
		} catch (Exception e) {
			throw new ParseException("Invalid delta-time value: " + dt);
		}
	}

	private static JSONObject parseWRTFactory(String v, Factory<?> factory) {

		// the value of v is either a tag for the type, or a tag:data where data is a
		// JSON structure corresponding to the data of that type. We split this
		// information
		// into variables 'type' and 'data'
		//
		int i = v.indexOf(":");
		String type = null;
		String data = null;
		if (i != -1) {
			type = v.substring(0, i);
			data = v.substring(i + 1);
		} else {
			type = v;
			data = "{}";
		}

		// look if the type is supported by the factory
		boolean found = false;
		for (JSONObject fe : factory.getInfo()) {
			if (type.equals(fe.getString("type"))) {
				found = true;
				break;
			}
		}

		// build a corresponding JSON for that data, if found
		JSONObject jo = null;
		if (found) {
			jo = new JSONObject();
			jo.put("type", type);
			jo.put("data", new JSONObject(data));
		}
		return jo;

	}

	private static void parseForceLawsOption(CommandLine line) throws ParseException {
		String fl = line.getOptionValue("fl", _forceLawsDefaultValue);
		_forceLawsInfo = parseWRTFactory(fl, _forceLawsFactory);
		if (_forceLawsInfo == null) {
			throw new ParseException("Invalid force laws: " + fl);
		}
	}

	private static void parseStateComparatorOption(CommandLine line) throws ParseException {
		String scmp = line.getOptionValue("cmp", _stateComparatorDefaultValue);
		_stateComparatorInfo = parseWRTFactory(scmp, _stateComparatorFactory);
		if (_stateComparatorInfo == null) {
			throw new ParseException("Invalid state comparator: " + scmp);
		}
	}


	//NEW METHODS--------------------------------------------------------------------------------------------
	private static void parseOutFileOption(CommandLine line) throws ParseException {
		_outFile = line.getOptionValue("o");
	}

	private static void parseStepsOption(CommandLine line) throws ParseException{
		String s = line.getOptionValue("s", _stepsDefaultValue.toString());
		try {
			_steps = Integer.parseInt(s);
			assert (_steps > 0);
		} catch (Exception e) {
			throw new ParseException("Invalid steps value: " + s);
		}
	}

	private static void parseExpectedOutputFileOption(CommandLine line) throws ParseException {
		 _eoFile = line.getOptionValue("eo");
	}


	private static void parseModeOption(CommandLine line) throws ParseException {
		_mode = line.getOptionValue("m", _modeDefaultValue).toLowerCase();
	}
	//NEW METHODS---------------------------------------------------------------------------------------------



	private static void startBatchMode() throws Exception {
		if (_inFile == null) {
			throw new ParseException("In batch mode an input file of bodies is required");
		}

		// Simulator
		PhysicsSimulator simulator = new PhysicsSimulator(_dtime, _forceLawsFactory.createInstance(_forceLawsInfo));

		// INPUT AND OUTPUT
			InputStream input = new FileInputStream(_inFile);
			OutputStream output = System.out;
			InputStream eoput = null;

			if(_outFile != null)
				output = new FileOutputStream(_outFile);

			if(_eoFile != null)
				eoput = new FileInputStream(_eoFile);

		//Comparator
		StateComparator stateComparator = _stateComparatorFactory.createInstance(_stateComparatorInfo);

		//Controller
		Controller controller = new Controller(simulator,_bodyFactory, _forceLawsFactory);
		controller.loadBodies(input);

		controller.run(_steps, output, eoput, stateComparator);

		//Closing Streams
		input.close();
		output.close();

		if (eoput != null)
			eoput.close();
	}


	private static void startGUIMode() throws Exception {
		// Simulator
		PhysicsSimulator simulator = new PhysicsSimulator(_dtime, _forceLawsFactory.createInstance(_forceLawsInfo));

		//Controller
		Controller controller = new Controller(simulator,_bodyFactory, _forceLawsFactory);


		//Input (optional)
		InputStream input = null;
		if(_inFile != null) {
			input = new FileInputStream(_inFile);
			controller.loadBodies(input);
		}

		SwingUtilities.invokeAndWait(new Runnable() {
			@Override
			public void run() {
				new MainWindow(controller);
			}
		});


		//Closing (optional) Stream
		if (input != null)
			input.close();

	}



	private static void start(String[] args) throws Exception {
		parseArgs(args);

		if (_mode.equals("gui")){
			startGUIMode();
		}else if(_mode.equals("batch")) {
			startBatchMode();
		} else throw new ParseException("No Mode Selected");

	}


	public static void main(String[] args) {
		try {
			init();
			start(args);
		} catch (Exception e) {
			System.err.println("Something went wrong ...");
			System.err.println();
			e.printStackTrace();
		}
	}
}
