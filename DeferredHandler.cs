using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using UnityEngine;

public class DeferredHandler : DeferredAction
{
	/// <summary>
	/// Schedule an <see cref="Action"/> to be executed later.
	/// </summary>
	/// <remarks>
	/// To cancel the action before it was executed, use <see cref="CancelExecution(Action)"/>.
	/// </remarks>
	static public void AddForLaterExecution(Action deferredAction, float delay)
	{
		DeferredData.Instance.deferredActions.Add(new DeferredAction { Action = deferredAction, ExecutionTime = Time.time + delay });
		DeferredData.Instance.deferredActions.Sort((a, b) => (int)(a.ExecutionTime - b.ExecutionTime));
	}

	/// <summary>
	/// Stop a previously scheduled <see cref="Action"/>.
	/// </summary>
	/// <returns>True, if the passed <see cref="action"/> was still scheduled.</returns>
	static public bool CancelExecution(Action action)
	{
		IEnumerable<DeferredAction> actionsToStop =
			DeferredData.Instance.deferredActions.Where
			(deferredAction =>
			deferredAction.Action == action);
		if (actionsToStop.Count() == 0)
		{
			return false;
		}

		DeferredData.Instance.deferredActions = 
		DeferredData.Instance.deferredActions.Except(actionsToStop).ToList();
		DeferredData.Instance.deferredActions.Sort();

		return true;
	}

	/// <summary>
	/// Stops all scheduled <see cref="Action"/>s.
	/// </summary>
	/// <remarks>
	/// You probably want to call this on scene changes.
	/// </remarks>
	static public void CancelExecution()
	{
		DeferredData.Instance.deferredActions.Clear();
	}
}
