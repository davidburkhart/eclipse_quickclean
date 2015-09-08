package quickclean.launchconfiguration;

import static quickclean.RefreshAndCleanWorkspace.QUICKCLEAN_REFRESH_AND_CLEAN_ENABLED;
import static quickclean.RefreshAndCleanWorkspace.QUICKCLEAN_REFRESH_AND_CLEAN_ENABLED_DEFAULT;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

public class LaunchConfigurationTab extends AbstractLaunchConfigurationTab {

	private Button checkbox;

	@Override
	public void createControl(Composite parent) {
		checkbox = createCheckButton(parent, "Enable refresh and clean after build");
		checkbox.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				updateLaunchConfigurationDialog();
			}
		});
		setControl(checkbox);
	}

	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
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
}
