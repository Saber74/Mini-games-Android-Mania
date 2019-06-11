package com.swing.along;

//https://code.google.com/archive/p/libgdx-users/wikis/IntegratingAndroidNativeUiElements3TierProjectSetup.wiki

public interface ActionResolver { public void showToast(CharSequence toastMessage, int toastDuration); public void showAlertBox(String alertBoxTitle, String alertBoxMessage, String alertBoxButtonText); public void openUri(String uri);public void showMyList(); }

