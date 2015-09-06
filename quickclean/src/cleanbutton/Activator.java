package cleanbutton;

import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IDebugEventSetListener;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

public class Activator extends AbstractUIPlugin {

	private static Activator plugin;

	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;

		DebugPlugin.getDefault().addDebugEventListener(new IDebugEventSetListener() {
			@Override
			public void handleDebugEvents(DebugEvent[] events) {
				for (DebugEvent event : events) {
					if (event.getKind() == DebugEvent.TERMINATE
							&& event.getSource().getClass().getName().startsWith("org.eclipse.ant")) {
						System.err.println("Ant done!");
						new ScheduleCleanAll().runClean();
					}
				}
			}
		});
	}

	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	public static Activator getDefault() {
		return plugin;
	}
}
