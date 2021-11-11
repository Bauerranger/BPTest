using System;
using System.Collections.Generic;
using System.Linq;
using UnityEngine;
using UnityEngine.Events;

/// <summary>
/// Executes events with a delay after a <see cref="Collider"/> entered the trigger.<br/>
/// Events will not be executed if the other collider leaves the trigger before the delay time has passed.
/// </summary>
/// <remarks>
/// Make sure you check <see cref="Collider.isTrigger"/> and configure it properly!
/// </remarks>
[RequireComponent(typeof(Collider))]
public class DelayedTrigger : MonoBehaviour
{
	[SerializeField]
	private UnityEvent delayedEvent;
	
	[SerializeField]
	private float delay;

	private DeferredExecutor deferredExecutor;

	private readonly List<PendingAction> pendingActions = new List<PendingAction>();

	private void Start()
	{
		deferredExecutor = DeferredExecutor.Instance;
	}

	private void OnTriggerEnter(Collider otherCollider)
	{
		var pendingAction = new PendingAction {Action = delayedEvent.Invoke, Collider = otherCollider};
		pendingActions.Add(pendingAction);

		// We schedule PendingAction.Invoke instead of delayedEvent.Invoke.
		// Otherwise we would cancel all scheduled delayedEvent.Invoke actions and not only the one for the otherCollider, when a collider leaves the trigger.
		deferredExecutor.ExecuteLater(pendingAction.Invoke, delay);
	}

	private void OnTriggerExit(Collider other)
	{
		PendingAction pendingAction = pendingActions.First(pa => pa.Collider == other);
		deferredExecutor.CancelExecution(pendingAction.Invoke);

		pendingActions.Remove(pendingAction);
	}

	private void OnDestroy()
	{
		foreach (PendingAction pendingAction in pendingActions)
		{
			deferredExecutor.CancelExecution(pendingAction.Invoke);
		}
	}

	private struct PendingAction
	{
		public Action Action;

		public Collider Collider;

		public void Invoke()
		{
			Action.Invoke();
		}
	}
}