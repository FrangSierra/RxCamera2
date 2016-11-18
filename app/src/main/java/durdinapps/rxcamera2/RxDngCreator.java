package durdinapps.rxcamera2;


import android.graphics.Bitmap;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.DngCreator;
import android.location.Location;
import android.media.Image;
import android.support.annotation.NonNull;
import android.util.Size;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;

import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;

public class RxDngCreator {

    private final DngCreator dngCreator;

    public RxDngCreator(final CameraCharacteristics characteristics, final CaptureResult metadata) {
        dngCreator = new DngCreator(characteristics, metadata);
    }

    public DngCreator getDngCreator() {
        return dngCreator;
    }

    @NonNull
    public RxDngCreator setDescription(String description) {
        dngCreator.setDescription(description);
        return this;
    }

    @NonNull
    public RxDngCreator setLocation(Location location) {
        dngCreator.setLocation(location);
        return this;
    }

    @NonNull
    public RxDngCreator setOrientation(int orientation) {
        dngCreator.setOrientation(orientation);
        return this;
    }

    @NonNull
    public RxDngCreator setThumbnail(Image pixels) {
        dngCreator.setThumbnail(pixels);
        return this;
    }

    @NonNull
    public RxDngCreator setThumbnail(Bitmap pixels) {
        dngCreator.setThumbnail(pixels);
        return this;
    }

    @NonNull
    Completable writeByteBuffer(@NonNull final OutputStream dngOutput,
                                @NonNull final Size size,
                                @NonNull final ByteBuffer pixels,
                                final long offset) {
        return Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(CompletableEmitter e) throws Exception {
                try {
                    dngCreator.writeByteBuffer(dngOutput, size, pixels, offset);
                    e.onComplete();
                } catch (IOException | IllegalStateException ex) {
                    e.onError(ex);
                }
            }
        });
    }

    @NonNull
    Completable writeImage(@NonNull final OutputStream dngOutput,
                           @NonNull final Image pixels) {
        return Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(CompletableEmitter e) throws Exception {
                try {
                    dngCreator.writeImage(dngOutput, pixels);
                    e.onComplete();
                } catch (IOException | IllegalStateException | IllegalArgumentException ex) {
                    e.onError(ex);
                }
            }
        });
    }

    @NonNull
    Completable writeInputStream(@NonNull final OutputStream dngOutput,
                                 @NonNull final Size size,
                                 @NonNull final InputStream pixels,
                                 final long offset) {
        return Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(CompletableEmitter e) throws Exception {
                try {
                    dngCreator.writeInputStream(dngOutput, size, pixels, offset);
                    e.onComplete();
                } catch (IOException | IllegalStateException | IllegalArgumentException ex) {
                    e.onError(ex);
                }
            }
        });
    }

}
