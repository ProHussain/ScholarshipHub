package com.hashmac.scholarshiphub.admin.repo;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.AsyncTask;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.hashmac.scholarshiphub.admin.dto.Scholarship;
import com.hashmac.scholarshiphub.admin.utils.RepoListener;

import org.json.JSONObject;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import timber.log.Timber;

public class ScholarshipRepository {
    private static final String YOUR_SERVER_KEY = "AAAACZY0ovs:APA91bG7LEXNtFsjQr4IYW3muLw32oRee8txPLEQz8F8ITcIPWNaS-XcOss2NWJpMog_6XGUGOIM2xWnu_8PppnrBs0lNZXsKNWC55TbZNOiu-2eYmj-h4kSaTuB_6C0aikk_K8GL8YM";
    private final StorageReference storageReference;
    private final FirebaseFirestore firestore;
    private static ScholarshipRepository instance;
    private static final String scholarshipCollection = "scholarships";
    private RepoListener listener;
    public static ScholarshipRepository getInstance() {
        if (instance == null) {
            instance = new ScholarshipRepository();
        }
        return instance;
    }

    public void setListener(RepoListener listener) {
        this.listener = listener;
    }

    private ScholarshipRepository() {
        storageReference = FirebaseStorage.getInstance().getReference();
        firestore = FirebaseFirestore.getInstance();
    }

    public void addScholarship(Scholarship scholarship, Uri uri) {
        storageReference.child("images/" + scholarship.getName()).putFile(uri).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Timber.d("Image uploaded successfully");
                storageReference.child("images/" + scholarship.getName()).getDownloadUrl().addOnCompleteListener(task1 -> {
                    if (task1.isSuccessful()) {
                        scholarship.setImageUrl(task1.getResult().toString());
                        saveInFireStore(scholarship);
                    } else {
                        listener.onRepoFailure("Error getting image url");
                        Timber.e("Error getting image url");
                    }
                });
            } else {
                listener.onRepoFailure("Error uploading image");
                Timber.e("Error uploading image");
            }
        });
    }

    private void saveInFireStore(Scholarship scholarship) {
        String id = firestore.collection(scholarshipCollection).document().getId();
        scholarship.setId(id);
        firestore.collection(scholarshipCollection).document(id).set(scholarship).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Timber.d("Scholarship added successfully");
                sendNotification(scholarship.getName(), "New scholarship available");
                listener.onRepoSuccess();
            } else {
                listener.onRepoFailure("Error uploading scholarship by "+task.getException().getMessage());
                Timber.e("Error uploading scholarship by "+task.getException().getMessage());
            }
        });
    }

    @SuppressLint("StaticFieldLeak")
    public void sendNotification(String title, String message) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    OkHttpClient client = new OkHttpClient();
                    JSONObject json = new JSONObject();
                    JSONObject notificationJson = new JSONObject();
                    notificationJson.put("title", title);
                    notificationJson.put("body", message);
                    JSONObject message = new JSONObject();
                    message.put("notification", notificationJson);
                    message.put("to", "/topics/scholarship");
                    json.put("message", message);
                    RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), message.toString());
                    Request request = new Request.Builder()
                            .header("Authorization", "key="+YOUR_SERVER_KEY)
                            .url("https://fcm.googleapis.com/fcm/send")
                            .post(body)
                            .build();
                    Response response = client.newCall(request).execute();
                    String finalResponse = response.body().string();
                    Timber.d("Notification sent successfully");
                    Timber.d(finalResponse);
                } catch (Exception e) {
                    Timber.e("Error sending notification");
                }
                return null;
            }
        }.execute();
    }

    public void deleteScholarship(Scholarship scholarship) {
        String imageUrl = scholarship.getName();
        firestore.collection(scholarshipCollection).document(scholarship.getId()).delete().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Timber.d("Scholarship deleted successfully");
                deleteImage(imageUrl);
            } else {
                listener.onRepoFailure("Error deleting scholarship");
                Timber.e("Error deleting scholarship");
            }
        });
    }

    public void deleteImage(String imageUrl) {
        storageReference.child("images/" + imageUrl).delete().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                listener.onRepoSuccess();
                Timber.d("Image deleted successfully");
            } else {
                listener.onRepoFailure("Error deleting image");
                Timber.e("Error deleting image");
            }
        });
    }

    public void fetchScholarships() {
        firestore.collection(scholarshipCollection).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<Scholarship> scholarships = task.getResult().toObjects(Scholarship.class);
                listener.onRepoFetch(scholarships);
            } else {
                Timber.e("Error fetching scholarships");
            }
        });
    }


    public void updateScholarship(Scholarship scholarship, Uri imageUri) {
        if (imageUri != null) {
            storageReference.child("images/" + scholarship.getName()).putFile(imageUri).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Timber.d("Image uploaded successfully");
                    storageReference.child("images/" + scholarship.getName()).getDownloadUrl().addOnCompleteListener(task1 -> {
                        if (task1.isSuccessful()) {
                            scholarship.setImageUrl(task1.getResult().toString());
                            updateInFireStore(scholarship);
                        } else {
                            listener.onRepoFailure("Error getting image url");
                            Timber.e("Error getting image url");
                        }
                    });
                } else {
                    listener.onRepoFailure("Error uploading image");
                    Timber.e("Error uploading image");
                }
            });
        } else {
            updateInFireStore(scholarship);
        }
    }

    private void updateInFireStore(Scholarship scholarship) {
        firestore.collection(scholarshipCollection).document(scholarship.getId()).set(scholarship).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Timber.d("Scholarship updated successfully");
                listener.onRepoSuccess();
            } else {
                listener.onRepoFailure("Error updating scholarship");
                Timber.e("Error updating scholarship");
            }
        });
    }
}
