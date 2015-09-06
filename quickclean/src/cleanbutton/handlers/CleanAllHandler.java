package cleanbutton.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import cleanbutton.ScheduleCleanAll;

public class CleanAllHandler extends AbstractHandler {

	public Object execute(ExecutionEvent event) throws ExecutionException {
		new ScheduleCleanAll().runClean();
		return null;
	}
}
