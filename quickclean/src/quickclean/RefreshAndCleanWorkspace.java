package quickclean;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.IDebugEventSetListener;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.model.IProcess;

public class RefreshAndCleanWorkspace implements IDebugEventSetListener {

	public static final String QUICKCLEAN_REFRESH_AND_CLEAN_ENABLED = "quickclean.REFRESH_AND_CLEAN_ENABLED";
	public static final boolean QUICKCLEAN_REFRESH_AND_CLEAN_ENABLED_DEFAULT = false;

	@Override
	public void handleDebugEvents(DebugEvent[] events) {
		for (DebugEvent event : events) {
			if (event.getKind() == DebugEvent.TERMINATE && event.getSource() instanceof IProcess) {
				IProcess process = (IProcess) event.getSource();
				ILaunchConfiguration launchConfiguration = process.getLaunch().getLaunchConfiguration();

				if (isRefreshAndCleanEnabled(launchConfiguration)) {
					new ScheduleRefreshAndClean(ResourcesPlugin.getWorkspace()).scheduleJob();
				}
			}
		}
	}

	private boolean isRefreshAndCleanEnabled(ILaunchConfiguration launchConfiguration) {
		try {
			return launchConfiguration.getAttribute(QUICKCLEAN_REFRESH_AND_CLEAN_ENABLED,
					QUICKCLEAN_REFRESH_AND_CLEAN_ENABLED_DEFAULT);
		} catch (CoreException e) {
			return false;
		}
	}
}