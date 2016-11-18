package durdinapps.rxcamera2;


import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.DngCreator;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;

public class RxDngCreator {

    public static Single<DngCreator> getDngCreator(final CameraCharacteristics characteristics, final CaptureResult metadata){
        return Single.create(new SingleOnSubscribe<DngCreator>() {
            @Override
            public void subscribe(SingleEmitter<DngCreator> e) throws Exception {
                e.onSuccess(new DngCreator(characteristics, metadata));
            }
        });
    }
}
