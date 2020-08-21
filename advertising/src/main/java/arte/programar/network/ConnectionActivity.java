package arte.programar.network;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.novoda.merlin.Bindable;
import com.novoda.merlin.Connectable;
import com.novoda.merlin.Disconnectable;
import com.novoda.merlin.Merlin;

public abstract class ConnectionActivity extends AppCompatActivity {
    private static final String TAG = "arte.programar::".concat(ConnectionActivity.class.getSimpleName());


    // Merlin Variable
    protected Merlin merlin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        merlin = createMerlin();
    }

    /**
     * Create Merlin
     *
     * @return new Merlin.Builder()
     * .withConnectableCallbacks().withDisconnectableCallbacks()
     * .withBindableCallbacks()
     * .build(context)
     */
    protected abstract Merlin createMerlin();

    protected void registerConnectable(Connectable connectable) {
        merlin.registerConnectable(connectable);
    }

    protected void registerDisconnectable(Disconnectable disconnectable) {
        merlin.registerDisconnectable(disconnectable);
    }

    protected void registerBindable(Bindable bindable) {
        merlin.registerBindable(bindable);
    }

    @Override
    protected void onStart() {
        super.onStart();
        merlin.bind();
    }

    @Override
    protected void onPause() {
        merlin.bind();
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        merlin.unbind();
    }
}
