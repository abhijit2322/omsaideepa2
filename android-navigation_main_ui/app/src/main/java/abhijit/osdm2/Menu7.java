package abhijit.osdm2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Belal on 18/09/16.
 */


@SuppressWarnings("MagicConstant")
public class Menu7 extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments

        System.out.println("Abhijit its coming report display menu>>>>>>>>>>>>>>>>>>>>>0");
        return inflater.inflate(R.layout.fragment_menu_3, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        System.out.println("Abhijit its coming report display menu>>>>>>>>>>>>>>>>>>>>>1");
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Menu 7");
    }
}
