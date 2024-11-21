package src;

public class GreenApple extends Card {
	public GreenApple(String text) {
		super(text);
	}

	@Override
	public String toString() {
		return getText();
	}
}