using System;
using System.Collections.Generic;
using System.Linq;
using UnityEngine;

/// <summary>
/// Like <see cref="Coroutine"/>s this can be used to execute an <see cref="Action"/> later.<br/>
/// However this is not bound to to a <see cref="GameObject"/> and thereby scheduled actions will not be cancelled when the game object is destroyed.<br/>
/// It can also be used by non-Unity classes.
/// </summary>
public class DeferredExecutor
{
	public static DeferredExecutor Instance { get; } = new DeferredExecutor();

	// All actions which are scheduled to be executed later.<br/>
	// We keep this list sorted by planned execution time.
	private List<DeferredAction> deferredActions = new List<DeferredAction>();

	/// <summary>
	/// Schedule an <see cref="Action"/> to be executed later.
	/// </summary>
	/// <remarks>
	/// To cancel the action before it was executed, use <see cref="CancelExecution(Action)"/>.
	/// </remarks>
	public void ExecuteLater(Action deferredAction, float delay)
	{
		deferredActions.Add(new DeferredAction {Action = deferredAction, ExecutionTime = Time.time + delay});
		deferredActions.Sort((a, b) => (int) (a.ExecutionTime - b.ExecutionTime));
	}

	/// <summary>
	/// Stop a previously scheduled <see cref="Action"/>.
	/// </summary>
	/// <returns>True, if the passed <see cref="action"/> was still scheduled.</returns>
	public bool CancelExecution(Action action)
	{
		IEnumerable<DeferredAction> actionsToStop =
			deferredActions.Where(deferredAction => deferredAction.Action == action);
		if (actionsToStop.Count() == 0)
		{
			return false;
		}

		deferredActions = deferredActions.Except(actionsToStop).ToList();
		deferredActions.Sort();

		return true;
	}

	/// <summary>
	/// Executes each scheduled action, if the delay time has passed.
	/// </summary>
	/// <remarks>
	/// This should be called from a Unity Update().
	/// </remarks>
	public void Update()
	{
		// because the actions are sorted, we only need to check the the very first one
		while (deferredActions.Count > 0 &&
		       deferredActions[0].ExecutionTime <= Time.time)
		{
			deferredActions[0].Action.Invoke();
			deferredActions.RemoveAt(0);
		}
	}

	/// <summary>
	/// Stops all scheduled <see cref="Action"/>s.
	/// </summary>
	/// <remarks>
	/// You probably want to call this on scene changes.
	/// </remarks>
	public void CancelExecution()
	{
		deferredActions.Clear();
	}

	private class DeferredAction
	{
		public Action Action;

		public float ExecutionTime;
	}
}
