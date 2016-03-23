import ar.com.nonosoft.jspec.SpecRunner;

import java.util.Stack;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

public class Suite {

	public static void main(String[] args)
	{
		new StackSpec().run();
		StackSpec();
	}

	private static void StackSpec() {
		new SpecRunner().describe(Stack.class, d -> {
			d.let("one", 1).let("two", 2);

			d.context("when create an empty stack", c -> {
				c.subject(new Stack());

				c.it("is empty", expect -> expect.that(c.subject().isEmpty(), is(true)));
			});

			d.context("when push new element onto top", c -> {
				c.subject(new Stack() {{ push(c.get("one")); }});

				c.it("has an element onto top", expect -> expect.that(c.subject().get(0), is(equalTo(c.get("one")))));
			});

			d.context("when pop the last element", (c) -> {
				c.subject(new Stack() {{ push(c.get("one")); push(c.get("two")); }});

				c.it("element is the last pushed", expect -> {expect.that(c.subject().pop(), is(equalTo(c.get("two")))); });
			});
		}).run();
	}
}