package ultraclean;

import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

public class QuickCleanPlugin extends AbstractUIPlugin {

	private static QuickCleanPlugin plugin;

	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;

		DebugPlugin.getDefault().addDebugEventListener(new RefreshAndCleanWorkspace());
	}

	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	public static QuickCleanPlugin getDefault() {
		return plugin;
	}
}
