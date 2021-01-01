package core.requests;

public class TestRequest {
	private int value;

	public TestRequest()
	{
		super();
	}
	
	public TestRequest(int value)
	{
		super();
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
}
