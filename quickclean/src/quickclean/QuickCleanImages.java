package quickclean;

import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;

public class QuickCleanImages {

	private static ImageRegistry imageRegistry;

	public static Image image(String path) {
		ensureImageRegistryInitialized();
		ensureImagePresent(path);
		return imageRegistry.get(path);
	}

	private static void ensureImageRegistryInitialized() {
		if (imageRegistry == null) {
			imageRegistry = new ImageRegistry();
		}
	}

	private static void ensureImagePresent(String path) {
		if (imageRegistry.get(path) == null) {
			String pluginId = QuickCleanPlugin.getDefault().getBundle().getSymbolicName();
			imageRegistry.put(path, AbstractUIPlugin.imageDescriptorFromPlugin(pluginId, path));
		}
	}
}
