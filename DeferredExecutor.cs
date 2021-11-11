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

	/// <summary>
	/// Executes each scheduled action, if the delay time has passed.
	/// </summary>
	/// <remarks>
	/// This should be called from a Unity Update().
	/// </remarks>
	public void Update()
	{
		// because the actions are sorted, we only need to check the the very first one
		while (DeferredData.Instance.deferredActions.Count > 0 &&
			   DeferredData.Instance.deferredActions[0].ExecutionTime <= Time.time)
		{
			DeferredData.Instance.deferredActions[0].Action.Invoke();
			DeferredData.Instance.deferredActions.RemoveAt(0);
		}
	}
}
