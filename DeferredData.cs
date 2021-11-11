using System.Collections.Generic;

public class DeferredData : DeferredAction
{
	public static DeferredData Instance { get; } = new DeferredData();

	// All actions which are scheduled to be executed later.<br/>
	// We keep this list sorted by planned execution time.
	public List<DeferredAction> deferredActions = new List<DeferredAction>();
}
