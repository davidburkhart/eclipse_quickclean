package ultraclean;

import org.eclipse.core.resources.IProject;
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

	@FunctionalInterface
	interface BuildAction {
		void run(int kind, IProgressMonitor progressMonitor) throws CoreException;
	}

	private final BuildAction buildAction;
	private final IResource resource;
	private final String name;

	public ScheduleRefreshAndClean(IWorkspace workspace) {
		buildAction = workspace::build;
		resource = workspace.getRoot();
		name = "workspace";
	}

	public ScheduleRefreshAndClean(IProject project) {
		buildAction = project::build;
		resource = project;
		name = project.getName();
	}

	public void scheduleJob() {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		WorkspaceJob cleanJob = new WorkspaceJob("Clean and build " + name) {
			@Override
			public boolean belongsTo(Object family) {
				return ResourcesPlugin.FAMILY_MANUAL_BUILD.equals(family);
			}

			@Override
			public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
				return clean(monitor);
			}
		};
		cleanJob.setRule(workspace.getRuleFactory().buildRule());
		cleanJob.setUser(true);
		cleanJob.setProperty(IProgressConstants2.SHOW_IN_TASKBAR_ICON_PROPERTY, Boolean.TRUE);
		cleanJob.schedule();
	}

	private IStatus clean(IProgressMonitor monitor) throws CoreException {

		monitor.beginTask("Refreshing and cleaning " + name, 3);

		monitor.subTask("Refreshing...");
		resource.refreshLocal(IResource.DEPTH_INFINITE, monitor);
		monitor.worked(1);

		monitor.subTask("Cleaning...");
		buildAction.run(IncrementalProjectBuilder.CLEAN_BUILD, monitor);
		monitor.worked(1);

		monitor.subTask("Building...");
		buildAction.run(IncrementalProjectBuilder.INCREMENTAL_BUILD, monitor);
		monitor.worked(1);

		return Status.OK_STATUS;
	}

}
