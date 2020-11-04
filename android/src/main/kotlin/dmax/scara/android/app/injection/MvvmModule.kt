package dmax.scara.android.app.injection

import dmax.scara.android.present.control.ControlContract
import dmax.scara.android.present.control.ControlModel
import dmax.scara.android.present.home.HomeContract
import dmax.scara.android.present.home.HomeModel
import dmax.scara.android.present.locate.LocateContract
import dmax.scara.android.present.locate.LocateModel
import dmax.scara.android.present.shapes.ShapesContract
import dmax.scara.android.present.shapes.ShapesModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

fun mvvm() = module {
    viewModel<HomeContract.Model> {
        HomeModel(
            connect = get(),
            disconnect = get(),
            isConnected = get(),
        )
    }
    viewModel<ControlContract.Model> {
        ControlModel(
            bendBase = get(),
            bendElbow = get(),
            bendWrist = get(),
            getAngles = get(),
        )
    }
    viewModel<LocateContract.Model> {
        LocateModel(
            move = get(),
            locate = get(),
        )
    }
    viewModel<ShapesContract.Model> {
        ShapesModel(
            click = get(),
            bendBaseInfiniteActor = get(),
            bendElbowInfiniteActor = get(),
            bendWristInfiniteActor = get(),
        )
    }
}
