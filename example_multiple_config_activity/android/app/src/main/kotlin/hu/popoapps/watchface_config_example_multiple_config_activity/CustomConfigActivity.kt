package hu.popoapps.watchface_config_example_multiple_config_activity

import hu.popoapps.watchface_config.ConfigActivity
import hu.popoapps.watchface_config_example_multiple_config_activity.watchface.WatchFaceExample1
import hu.popoapps.watchface_config_example_multiple_config_activity.watchface.WatchFaceExample2
import hu.popoapps.wearable_flutter_fragment_application.core.DismissibleApplicationConfigurator
import hu.popoapps.wearable_flutter_fragment_application.core.FlutterComponentsProvider
import hu.popoapps.wearable_flutter_fragment_application.layout.DismissibleLayout

class CustomConfigActivity : ConfigActivity() {

    override fun setDismissibleApplicationConfigurator(dismissibleLayout: DismissibleLayout) {
        dismissibleApplicationConfigurator = DismissibleApplicationConfigurator(
            getDefaultDismissableApplicationConfiguration(dismissibleLayout),
            FlutterComponentsProvider(
                context = this,
                entrypointName = getCustomMethod(
                    mapOf(
                        WatchFaceExample1::class.java to "main",
                        WatchFaceExample2::class.java to "secondary"
                    ),
                )
            )
        )
    }

    override fun getWatchFaceName(): String {
        return getCustomWatchFaceName(
            mapOf(
                WatchFaceExample1::class.java to "First Example Watch Face",
                WatchFaceExample2::class.java to "Second Example Watch Face"
            ),
        )
    }
}
