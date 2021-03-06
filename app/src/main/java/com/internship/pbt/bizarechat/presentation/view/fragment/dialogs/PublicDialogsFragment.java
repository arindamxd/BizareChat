package com.internship.pbt.bizarechat.presentation.view.fragment.dialogs;


import android.os.Bundle;
import android.support.annotation.Keep;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androidadvance.topsnackbar.TSnackbar;
import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.internship.pbt.bizarechat.R;
import com.internship.pbt.bizarechat.adapter.DialogsRecyclerViewAdapter;
import com.internship.pbt.bizarechat.constans.DialogsType;
import com.internship.pbt.bizarechat.data.cache.CacheUsersPhotos;
import com.internship.pbt.bizarechat.data.datamodel.DialogModel;
import com.internship.pbt.bizarechat.data.repository.ContentDataRepository;
import com.internship.pbt.bizarechat.data.repository.DialogsDataRepository;
import com.internship.pbt.bizarechat.data.repository.UserDataRepository;
import com.internship.pbt.bizarechat.domain.events.DialogsUpdatedEvent;
import com.internship.pbt.bizarechat.domain.interactor.DeleteDialogUseCase;
import com.internship.pbt.bizarechat.domain.interactor.GetAllDialogsUseCase;
import com.internship.pbt.bizarechat.domain.interactor.GetPhotoUseCase;
import com.internship.pbt.bizarechat.domain.interactor.GetUnreadMessagesCountUseCase;
import com.internship.pbt.bizarechat.domain.interactor.GetUserByIdUseCase;
import com.internship.pbt.bizarechat.presentation.BizareChatApp;
import com.internship.pbt.bizarechat.presentation.presenter.dialogs.DialogsPresenterImp;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class PublicDialogsFragment extends MvpAppCompatFragment
        implements DialogsView, DialogsRecyclerViewAdapter.OnDialogDeleteCallback,
        SwipeRefreshLayout.OnRefreshListener,
        DialogsRecyclerViewAdapter.OnDialogClickCallback {
    private final int menuSearchId = 100;

    @InjectPresenter
    DialogsPresenterImp presenter;

    private OnPublicDialogClickListener dialogClickListener;

    private RecyclerView recyclerView;
    private LinearLayoutManager mLayoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TSnackbar connProblemSnack;

    @ProvidePresenter
    DialogsPresenterImp provideNewDialogsPresenter() {
        return new DialogsPresenterImp(
                new DeleteDialogUseCase(new DialogsDataRepository(BizareChatApp.getInstance()
                        .getDialogsService(), BizareChatApp.getInstance().getDaoSession())),
                new GetPhotoUseCase(new ContentDataRepository(
                        BizareChatApp.getInstance().getContentService(),
                        CacheUsersPhotos.getInstance(BizareChatApp.getInstance()))),
                new GetUserByIdUseCase(new UserDataRepository(
                        BizareChatApp.getInstance().getUserService())),
                new GetUnreadMessagesCountUseCase(new DialogsDataRepository(BizareChatApp.getInstance()
                        .getDialogsService(), BizareChatApp.getInstance().getDaoSession())),
                new GetAllDialogsUseCase(new DialogsDataRepository(BizareChatApp.getInstance()
                        .getDialogsService(), BizareChatApp.getInstance().getDaoSession())),
                BizareChatApp.getInstance().getDaoSession(),
                DialogsType.PUBLIC_GROUP_CHAT);
    }

    public void setDialogClickListener(OnPublicDialogClickListener dialogClickListener) {
        this.dialogClickListener = dialogClickListener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialogs, container, false);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView = (RecyclerView) view.findViewById(R.id.dialogs_recycler);
        recyclerView.setLayoutManager(mLayoutManager);
        ((SimpleItemAnimator)recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.dialogs_swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);

        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.loadDialogs();
        recyclerView.setAdapter(presenter.getAdapter());
        presenter.getAdapter().setContext(getActivity());
        presenter.getAdapter().setOnDialogClickCallback(this);
        presenter.refreshDialogsInfo();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
        stopRefreshing();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.add(0, menuSearchId, 0, "Search").setIcon(R.drawable.search_icon)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == menuSearchId) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Subscribe
    @Keep
    public void onDialogsUpdated(DialogsUpdatedEvent event) {
        presenter.onDialogsUpdated();
    }

    @Override
    public void onDialogDelete(int position) {
        //presenter.deleteDialog(position);
    }

    @Override
    public void onDialogClick(int position) {
        presenter.onDialogClick(position);
    }

    @Override
    public void showChatRoom(DialogModel dialogModel) {
        dialogClickListener.onPublicDialogClick(dialogModel);
    }

    @Override
    public void onRefresh() {
        presenter.refreshDialogsInfo();
    }

    @Override
    public void stopRefreshing() {
        swipeRefreshLayout.setRefreshing(false);
    }

    public interface OnPublicDialogClickListener {
        void onPublicDialogClick(DialogModel dialog);
    }

    @Override
    public void showNetworkError() {
        if (connProblemSnack == null) {
            connProblemSnack = TSnackbar.make(recyclerView,
                    R.string.main_connection_problem, TSnackbar.LENGTH_SHORT);
            connProblemSnack.getView().setBackgroundColor(getResources()
                    .getColor(R.color.main_screen_connection_problem_background));
            TextView message = (TextView) connProblemSnack.getView()
                    .findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
            message.setTextColor(getResources().getColor(R.color.main_screen_connection_problem_text));
            message.setGravity(Gravity.CENTER);
        }
        connProblemSnack.show();
    }
}