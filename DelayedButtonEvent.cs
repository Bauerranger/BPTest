using System;
using UnityEngine;
using UnityEngine.Events;
using UnityEngine.UI;

/// <summary>
/// Executes events with a delay after the <see cref="Button"/> has been clicked.
/// </summary>
public class DelayedButtonEvent : MonoBehaviour
{
	[SerializeField]
	private UnityEvent delayedEvent;

	[SerializeField]
	private float delay;

	[SerializeField]
	private bool cancelDelayedEventOnDestroy;

	private Button button;

	private void Awake()
	{
		button = FindObjectOfType<Button>();

		button.onClick.AddListener(() => OnButtonClick(delayedEvent.Invoke, delay));
	}

	private static void OnButtonClick(Action delayedAction, float delay)
	{
		DeferredExecutor.Instance.ExecuteLater(delayedAction, delay);
	}

	private void OnDestroy()
	{
		button.onClick.RemoveListener(() => OnButtonClick(delayedEvent.Invoke, delay));

		if (cancelDelayedEventOnDestroy)
		{
			DeferredExecutor.Instance.CancelExecution();
		}
		else
		{
			// Do NOT call DeferredExecutor.CancelExecution.
			// We want the delayed event to be invoked even if the button has already been destroyed.
		}
	}
}