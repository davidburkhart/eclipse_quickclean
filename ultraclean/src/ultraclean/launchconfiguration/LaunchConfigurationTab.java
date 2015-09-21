package ultraclean.launchconfiguration;

import static ultraclean.RefreshAndCleanWorkspace.QUICKCLEAN_REFRESH_AND_CLEAN_ENABLED;
import static ultraclean.RefreshAndCleanWorkspace.QUICKCLEAN_REFRESH_AND_CLEAN_ENABLED_DEFAULT;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import ultraclean.QuickCleanImages;

public class LaunchConfigurationTab extends AbstractLaunchConfigurationTab {

	private Button checkbox;

	@Override
	public void createControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(1, false));
		checkbox = createCheckButton(composite, "Enable refresh and clean after build");
		checkbox.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				updateLaunchConfigurationDialog();
			}
		});
		setControl(composite);

	}

	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {
		boolean value;
		try {
			value = configuration.getAttribute(QUICKCLEAN_REFRESH_AND_CLEAN_ENABLED,
					QUICKCLEAN_REFRESH_AND_CLEAN_ENABLED_DEFAULT);
		} catch (CoreException e) {
			value = QUICKCLEAN_REFRESH_AND_CLEAN_ENABLED_DEFAULT;
		}
		checkbox.setSelection(value);
	}

	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		boolean value = checkbox.getSelection();
		setAttribute(QUICKCLEAN_REFRESH_AND_CLEAN_ENABLED, configuration, value,
				QUICKCLEAN_REFRESH_AND_CLEAN_ENABLED_DEFAULT);
	}

	@Override
	public String getName() {
		return "Refresh and clean";
	}

	@Override
	public Image getImage() {
		return QuickCleanImages.image("icons/edit-clear.png");
	}

	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
	}

}
