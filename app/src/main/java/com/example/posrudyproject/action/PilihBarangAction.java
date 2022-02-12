package com.example.posrudyproject.action;

import com.example.posrudyproject.Interface.OnStepItemListener;
import com.example.posrudyproject.ui.produk.fragment.PilihCustomBarangFragment;

public class PilihBarangAction implements OnStepItemListener {

    int stepIndex = 0;
    Class<PilihCustomBarangFragment> fragmentClass = PilihCustomBarangFragment.class;

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

    public Class<PilihCustomBarangFragment> getFragmentClass() {
        return fragmentClass;
    }

    public void setFragmentClass(Class<PilihCustomBarangFragment> fragmentClass) {
        this.fragmentClass = fragmentClass;
    }
}
