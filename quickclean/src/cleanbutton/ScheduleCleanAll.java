package cleanbutton;

import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.progress.IProgressConstants2;

public class ScheduleCleanAll {

	public void runClean() {
		WorkspaceJob cleanJob = new WorkspaceJob("Clean and build workspace") {
			@Override
			public boolean belongsTo(Object family) {
				return ResourcesPlugin.FAMILY_MANUAL_BUILD.equals(family);
			}

			@Override
			public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
				return cleanAll(monitor);
			}
		};
		cleanJob.setRule(ResourcesPlugin.getWorkspace().getRuleFactory().buildRule());
		cleanJob.setUser(true);
		cleanJob.setProperty(IProgressConstants2.SHOW_IN_TASKBAR_ICON_PROPERTY, Boolean.TRUE);
		cleanJob.schedule();
	}

	private IStatus cleanAll(IProgressMonitor monitor) throws CoreException {
		monitor.beginTask("Clean all projects", 2);

		monitor.subTask("Cleaning...");
		ResourcesPlugin.getWorkspace().build(IncrementalProjectBuilder.CLEAN_BUILD, monitor);
		monitor.worked(1);

		monitor.subTask("Building...");
		ResourcesPlugin.getWorkspace().build(IncrementalProjectBuilder.INCREMENTAL_BUILD, monitor);
		monitor.worked(1);

		return Status.OK_STATUS;
	}
}
