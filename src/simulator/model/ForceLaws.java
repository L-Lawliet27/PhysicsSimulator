package simulator.model;

import simulator.misc.Vector2D;

import java.util.List;

public interface ForceLaws {
	public void apply(List<Body> bs);
}
