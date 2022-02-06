package com.example.posrudyproject.action;

import com.example.posrudyproject.Interface.OnStepItemListener;
import com.example.posrudyproject.ui.produk.fragment.PilihBarangFragment;

public class PilihBarangAction implements OnStepItemListener {

    int stepIndex = 0;
    Class<PilihBarangFragment> fragmentClass = PilihBarangFragment.class;

    @Override
    public void prev() {

    }

    @Override
    public void next() {

    }

    @Override
    public void submit() {

    }

    public int getStepIndex() {
        return stepIndex;
    }

    public void setStepIndex(int stepIndex) {
        this.stepIndex = stepIndex;
    }

    public Class<PilihBarangFragment> getFragmentClass() {
        return fragmentClass;
    }

    public void setFragmentClass(Class<PilihBarangFragment> fragmentClass) {
        this.fragmentClass = fragmentClass;
    }
}
