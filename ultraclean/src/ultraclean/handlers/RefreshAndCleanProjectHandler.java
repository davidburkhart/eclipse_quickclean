package ultraclean.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

import ultraclean.ScheduleRefreshAndClean;

public class RefreshAndCleanProjectHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IProject project = selectedProject(event);
		if (project != null) {
			new ScheduleRefreshAndClean(project).scheduleJob();
		}
		return null;
	}

	private IProject selectedProject(ExecutionEvent event) {
		ISelection selection = HandlerUtil.getCurrentSelection(event);
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection structuredSelection = (IStructuredSelection) selection;
			return toProject(structuredSelection.getFirstElement());
		}
		return null;
	}

	private IProject toProject(Object firstElement) {
		if (firstElement instanceof IProject) {
			return (IProject) firstElement;
		}

		if (firstElement instanceof IAdaptable) {
			IAdaptable adaptable = (IAdaptable) firstElement;
			return adaptable.getAdapter(IProject.class);
		}

		return null;
	}

}
