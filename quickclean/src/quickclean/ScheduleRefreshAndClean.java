package quickclean;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.progress.IProgressConstants2;

public class ScheduleRefreshAndClean {

	public void scheduleJob() {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		WorkspaceJob cleanJob = new WorkspaceJob("Clean and build workspace") {
			@Override
			public boolean belongsTo(Object family) {
				return ResourcesPlugin.FAMILY_MANUAL_BUILD.equals(family);
			}

			@Override
			public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
				return cleanAll(workspace, monitor);
			}
		};
		cleanJob.setRule(workspace.getRuleFactory().buildRule());
		cleanJob.setUser(true);
		cleanJob.setProperty(IProgressConstants2.SHOW_IN_TASKBAR_ICON_PROPERTY, Boolean.TRUE);
		cleanJob.schedule();
	}

	private IStatus cleanAll(IWorkspace workspace, IProgressMonitor monitor) throws CoreException {

		monitor.beginTask("Refreshing and cleaning all projects", 3);

		monitor.subTask("Refreshing...");
		workspace.getRoot().refreshLocal(IResource.DEPTH_INFINITE, monitor);
		monitor.worked(1);

		monitor.subTask("Cleaning...");
		workspace.build(IncrementalProjectBuilder.CLEAN_BUILD, monitor);
		monitor.worked(1);

		monitor.subTask("Building...");
		workspace.build(IncrementalProjectBuilder.INCREMENTAL_BUILD, monitor);
		monitor.worked(1);

		return Status.OK_STATUS;
	}
}
