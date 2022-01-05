package com.gap.bis_inspection.activity.message;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.gap.bis_inspection.R;
import com.gap.bis_inspection.adapter.message.ChatGroupAdapter;
import com.gap.bis_inspection.app.AppController;
import com.gap.bis_inspection.common.Constants;
import com.gap.bis_inspection.db.enumtype.GeneralStatus;
import com.gap.bis_inspection.db.manager.DatabaseManager;
import com.gap.bis_inspection.db.objectmodel.AppUser;
import com.gap.bis_inspection.db.objectmodel.ChatGroup;
import com.gap.bis_inspection.db.objectmodel.ChatGroupMember;
import com.gap.bis_inspection.db.objectmodel.User;
import com.gap.bis_inspection.exception.WebServiceException;
import com.gap.bis_inspection.service.CoreService;
import com.gap.bis_inspection.service.Services;
import com.gap.bis_inspection.webservice.MyPostJsonService;
import com.gap.bis_inspection.widget.menudrawer.ListDrawer;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

public class ChatGroupListActivity extends AppCompatActivity {
    ListView groupListView;
    RecyclerView recyclerView;
    CoreService coreService;
    DrawerLayout drawerlayout;
    RelativeLayout rel, menuIcon;
    Handler handler;
    AppController application;
    ArrayList<ChatGroup> userChatGroupList;
    List<Long> longListView = new ArrayList<>();
    private Services services;
    private DatabaseManager databaseManager;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                // String token = instanceIdResult.getToken();
                System.out.println("getToken======" + instanceIdResult.getToken());
                sendTokenToServer(instanceIdResult.getToken());
            }
        });

        databaseManager = new DatabaseManager(this);
        coreService = new CoreService(databaseManager);
        //AlarmManagerUtil.scheduleChatMessageReceiver(this);

        /**
         * application = (AppController) getApplication(); */

        services = new Services(getApplicationContext());
        //services.getChatMessageStatusList();

        application = (AppController) getApplication();
        application.setCurrentEntityName(AppController.ENTITY_NAME_NOTIFICATION);
        application.setCurrentEntityId(null);
        user = application.getCurrentUser();


        new ChatGroupMemberList().execute();

        menuIcon = (RelativeLayout) findViewById(R.id.menu_Icon);
        groupListView = (ListView) findViewById(R.id.groupList);
        recyclerView = (RecyclerView) findViewById(R.id.listView_drawer);
        rel = (RelativeLayout) findViewById(R.id.rel);
        TextView webSite = (TextView) findViewById(R.id.webSite_TV);
        refreshChatGroupList();



        drawerlayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ListDrawer drawerlist = new ListDrawer(ChatGroupListActivity.this, drawerlayout, rel, recyclerView);
        drawerlist.addListDrawer();

        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawerlayout.isDrawerOpen(rel))
                    drawerlayout.closeDrawer(rel);
                else
                    drawerlayout.openDrawer(rel);
            }
        });

        //updateList();
        //getMessage();


        groupListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                ChatGroup chatGroup = (ChatGroup) parent.getItemAtPosition(position);
                Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
                intent.putExtra("chatGroupId", chatGroup.getId());
                intent.putExtra("chatGroupName", chatGroup.getName());
                //System.out.println("chatGroupId=====" + chatGroup.getServerGroupId());
                //System.out.println("chatGroupId=====" + chatGroup.getServerGroupId());
                startActivity(intent);
            }
        });

        webSite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("http://www.gapcom.ir"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
    }

    private void updateList() {
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshChatGroupList();
                handler.postDelayed(this, 1000);
            }
        }, 1000);
    }

    private void refreshChatGroupList() {

        userChatGroupList = (ArrayList<ChatGroup>) coreService.getActiveChatGroupList();
        if (userChatGroupList != null) {
            for (ChatGroup userChatGroup : userChatGroupList) {
                userChatGroup.setLastChatMessage(coreService.getLastChatMessageByGroup(userChatGroup.getId()));
                userChatGroup.setCountOfUnreadMessage(coreService.getCountOfUnreadMessageByGroup(userChatGroup.getId(), application.getCurrentUser().getServerUserId()));
            }
            ChatGroupAdapter userChatGroupAdapter = new ChatGroupAdapter(getApplicationContext(), R.layout.user_chat_group_list, userChatGroupList);
            groupListView.setAdapter(userChatGroupAdapter);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        refreshChatGroupList();
    }

    private void getMessage() {
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {


                handler.postDelayed(this, 100000);
            }
        }, 100000);
    }



    @SuppressLint("StaticFieldLeak")
    private class ChatGroupMemberList extends AsyncTask<Void, Void, Void> {
        private String result;
        private String errorMsg;

        @SuppressLint("StringFormatInvalid")
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            try {
                if (result != null) {

                    JSONObject resultJson = new JSONObject(result);
                    if (!resultJson.isNull(Constants.SUCCESS_KEY)) {
                        if (!resultJson.isNull(Constants.RESULT_KEY)) {
                            JSONObject resultJsonObject = resultJson.getJSONObject(Constants.RESULT_KEY);
                            List<Long> serverGroupIdList = new ArrayList<Long>();
                            if (!resultJsonObject.isNull("ChatGroupMemberList")) {
                                JSONArray chatGroupJsonArray = resultJsonObject.getJSONArray("ChatGroupMemberList");
                                for (int i = 0; i < chatGroupJsonArray.length(); i++) {
                                    JSONObject chatGroupJsonObject = chatGroupJsonArray.getJSONObject(i);
                                    if (!chatGroupJsonObject.isNull("id")) {
                                        Long serverGroupId = chatGroupJsonObject.getLong("id");
                                        serverGroupIdList.add(serverGroupId);
                                        ChatGroup tmpChatGroupFS = new ChatGroup();
                                        tmpChatGroupFS.setServerGroupId(serverGroupId);
                                        ChatGroup chatGroup = coreService.getChatGroupByServerGroupId(tmpChatGroupFS);
                                        if (chatGroup == null) {
                                            chatGroup = new ChatGroup();
                                            chatGroup.setServerGroupId(serverGroupId);
                                        }
                                        if (!chatGroupJsonObject.isNull("name")) {
                                            chatGroup.setName(chatGroupJsonObject.getString("name"));
                                        }
                                        if (!chatGroupJsonObject.isNull("maxMember")) {
                                            chatGroup.setMaxMember(chatGroupJsonObject.getInt("maxMember"));
                                        }
                                        if (!chatGroupJsonObject.isNull("notifyAct")) {
                                            if (chatGroup.getId() == null) {
                                                chatGroup.setNotifyAct(chatGroupJsonObject.getBoolean("notifyAct"));
                                            }
                                        }
                                        if (!chatGroupJsonObject.isNull("status")) {
                                            chatGroup.setStatusEn(chatGroupJsonObject.getInt("status"));
                                        }
                                        if (chatGroup.getId() == null) {
                                            chatGroup = coreService.saveChatGroup(chatGroup);

                                        } else {
                                            coreService.updateChatGroup(chatGroup);
                                        }

                                        List<Long> userIdList = new ArrayList<Long>();
                                        if (!chatGroupJsonObject.isNull("chatGroupMembers")) {
                                            JSONArray chatGroupMemberJsonArray = chatGroupJsonObject.getJSONArray("chatGroupMembers");
                                            for (int j = 0; j < chatGroupMemberJsonArray.length(); j++) {
                                                JSONObject chatGroupMemberJsonObject = chatGroupMemberJsonArray.getJSONObject(j);

                                                if (!chatGroupMemberJsonObject.isNull("userId")) {
                                                    Long userId = chatGroupMemberJsonObject.getLong("userId");
                                                    userIdList.add(userId);
                                                    ChatGroupMember tmpChatGroupMemberFS = new ChatGroupMember();
                                                    tmpChatGroupMemberFS.setAppUserId(userId);
                                                    tmpChatGroupMemberFS.setChatGroupId(chatGroup.getId());
                                                    ChatGroupMember chatGroupMember = coreService.getChatGroupMemberByUserAndGroup(tmpChatGroupMemberFS);
                                                    if (chatGroupMember == null) {
                                                        chatGroupMember = new ChatGroupMember();
                                                        chatGroupMember.setAppUserId(userId);
                                                        chatGroupMember.setChatGroupId(chatGroup.getId());

                                                    }
                                                    if (!chatGroupMemberJsonObject.isNull("privilegeTypeEn")) {
                                                        chatGroupMember.setPrivilegeTypeEn(chatGroupMemberJsonObject.getInt("privilegeTypeEn"));
                                                    }
                                                    if (!chatGroupMemberJsonObject.isNull("adminIs")) {
                                                        chatGroupMember.setAdminIs(chatGroupMemberJsonObject.getBoolean("adminIs"));
                                                    }

                                                    if (chatGroupMember.getId() == null) {
                                                        coreService.saveChatGroupMember(chatGroupMember);
                                                    } else {
                                                        coreService.updateChatGroupMember(chatGroupMember);
                                                    }

                                                    AppUser appUser = coreService.getAppUserById(chatGroupMember.getAppUserId());
                                                    if (appUser == null) {
                                                        appUser = new AppUser();
                                                        getUserById(getApplicationContext(), user, chatGroupMember.getAppUserId(), appUser, false);
                                                    } else if (appUser.getName() == null || appUser.getFamily() == null) {
                                                        getUserById(getApplicationContext(), user, chatGroupMember.getAppUserId(), appUser, true);
                                                    }
                                                }
                                            }
                                        }
                                        ChatGroupMember tmpChatGroupMemberFS = new ChatGroupMember();
                                        tmpChatGroupMemberFS.setChatGroupId(chatGroup.getId());
                                        tmpChatGroupMemberFS.setNotAppUserIdList(userIdList);
                                        List<ChatGroupMember> ChatGroupMemberRemovedList = coreService.getChatGroupMemberListByParam(tmpChatGroupMemberFS);
                                        for (ChatGroupMember ChatGroupMemberRemoved : ChatGroupMemberRemovedList) {
                                            coreService.deleteChatGroupMember(ChatGroupMemberRemoved);
                                        }
                                    }
                                }
                            }
                            ChatGroup tmpChatGroupFS = new ChatGroup();
                            tmpChatGroupFS.setNotServerGroupIdList(serverGroupIdList);
                            List<ChatGroup> chatGroupUserRemovedList = coreService.getChatGroupListByParam(tmpChatGroupFS);
                            for (ChatGroup chatGroupUserRemoved : chatGroupUserRemovedList) {
                                chatGroupUserRemoved.setStatusEn(GeneralStatus.Inactive.ordinal());
                                coreService.updateChatGroup(chatGroupUserRemoved);
                            }
                        }
                    }
                }
            } catch (JSONException e) {
                String errorMsg = e.getMessage();
                if (errorMsg == null) {
                    errorMsg = "ChatMessageReceiver";
                }
                Log.d(errorMsg, errorMsg);
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.Some_error_accor_contact_admin), Toast.LENGTH_LONG).show();
            }

        }

        @Override
        protected Void doInBackground(Void... voids) {

            JSONObject jsonObject = new JSONObject();
            AppController application = (AppController) getApplication();
            try {
                jsonObject.put("username", application.getCurrentUser().getUsername());
                jsonObject.put("tokenPass", application.getCurrentUser().getBisPassword());
                MyPostJsonService postJsonService = new MyPostJsonService(databaseManager, getApplicationContext());
                result = postJsonService.sendData("getUserChatGroupMemberList", jsonObject, true);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (WebServiceException e) {
                e.printStackTrace();
            } catch (SocketException e) {
                e.printStackTrace();
            } catch (SocketTimeoutException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public void getUserById(Context context, User user, Long userId, AppUser appUser, boolean isUpdate) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("username", user.getUsername());
            jsonObject.put("tokenPass", user.getBisPassword());
            jsonObject.put("id", userId);

            MyPostJsonService postJsonService = new MyPostJsonService(databaseManager, context);
            try {
                String result = postJsonService.sendData("getUserInfoById", jsonObject, true);

                if (result != null) {
                    try {
                        JSONObject resultJson = new JSONObject(result);
                        if (!resultJson.isNull(Constants.SUCCESS_KEY)) {
                            if (!resultJson.isNull(Constants.RESULT_KEY)) {
                                JSONObject resultJsonObject = resultJson.getJSONObject(Constants.RESULT_KEY);
                                if (!resultJsonObject.isNull("user")) {
                                    JSONObject userJsonObject = resultJsonObject.getJSONObject("user");

                                    if (!userJsonObject.isNull("id")) {
                                        appUser.setId(userJsonObject.getLong("id"));
                                    }
                                    if (!userJsonObject.isNull("name")) {
                                        appUser.setName(userJsonObject.getString("name"));
                                    }
                                    if (!userJsonObject.isNull("family")) {
                                        appUser.setFamily(userJsonObject.getString("family"));
                                    }

                                    if (isUpdate) {
                                        coreService.updateAppUser(appUser);
                                    } else {
                                        coreService.insertAppUser(appUser);
                                    }

                                }

                            }
                        }
                    } catch (JSONException e) {
                        String errorMsg = e.getMessage();
                        if (errorMsg == null) {
                            errorMsg = "ChatMessageReceiver";
                        }
                        Log.d(errorMsg, errorMsg);
                        Toast.makeText(context, getResources().getString(R.string.Some_error_accor_contact_admin), Toast.LENGTH_LONG).show();
                    }
                }

            } catch (SocketTimeoutException e) {
                String errorMsg = e.getMessage();
                if (errorMsg == null) {
                    errorMsg = "SocketTimeoutException";
                }
                Log.d(errorMsg, errorMsg);
            } catch (SocketException | WebServiceException e) {
                String errorMsg = e.getMessage();
                if (errorMsg == null) {
                    errorMsg = "ChatMessageReceiver";
                }
                Log.d(errorMsg, errorMsg);
            }

        } catch (JSONException e) {
            String errorMsg = e.getMessage();
            if (errorMsg == null) {
                errorMsg = "RegistrationFragment";
            }
            Log.d(errorMsg, errorMsg);
        }
    }

    public void sendTokenToServer(final String token) {

        class GetToken extends AsyncTask<Void, Void, Void> {
            private String result;
            private String errorMsg;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
            }

            @Override
            protected Void doInBackground(Void... voids) {

                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("username", user.getUsername());
                    jsonObject.put("tokenPass", user.getBisPassword());
                    jsonObject.put("firebaseTokenId", token);
                    MyPostJsonService postJsonService = new MyPostJsonService(null, ChatGroupListActivity.this);
                    try {
                        result = postJsonService.sendData("updateFirebaseTokenId", jsonObject, true);
                    } catch (SocketTimeoutException e) {
                        errorMsg = getResources().getString(R.string.Some_error_accor_contact_admin);
                    } catch (SocketException e) {
                        errorMsg = getResources().getString(R.string.Some_error_accor_contact_admin);
                    } catch (WebServiceException e) {
                        Log.d("RegistrationFragment", e.getMessage());
                    }

                } catch (JSONException e) {
                    Log.d("RegistrationFragment", e.getMessage());
                }

                return null;
            }

        }

        new GetToken().execute();
    }
}


