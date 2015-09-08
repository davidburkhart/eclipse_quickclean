package quickclean.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import quickclean.ScheduleRefreshAndClean;

public class RefreshAndCleanAllHandler extends AbstractHandler {

	public Object execute(ExecutionEvent event) throws ExecutionException {
		new ScheduleRefreshAndClean().scheduleJob();
		return null;
	}
}
