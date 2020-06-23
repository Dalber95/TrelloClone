package pl.krusiec.trelloclone.firebase

import android.app.Activity
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import pl.krusiec.trelloclone.activities.*
import pl.krusiec.trelloclone.models.Board

import pl.krusiec.trelloclone.models.User
import pl.krusiec.trelloclone.utils.Constants

class FirestoreClass {

    private val fireStore = FirebaseFirestore.getInstance()

    fun registerUser(activity: SignUpActivity, userInfo: User) {
        fireStore.collection(Constants.USERS)
            .document(getCurrentUserId())
            .set(userInfo, SetOptions.merge())
            .addOnSuccessListener {
                activity.userRegisteredSuccess()
            }
    }

    fun createBoard(activity: CreateBoardActivity, board: Board) {
        fireStore.collection(Constants.BOARDS)
            .document()
            .set(board, SetOptions.merge())
            .addOnSuccessListener {
                Log.e(activity.javaClass.simpleName, "Board created successfully.")
                Toast.makeText(activity, "Board created successfully.", Toast.LENGTH_SHORT).show()
                activity.boardCreatedSuccessfully()
            }.addOnFailureListener {exception ->
                activity.hideProgressDialog()
                Log.e(activity.javaClass.simpleName, "Error while creating a board.", exception)
            }
    }

    fun updateUserProfileData(activity: MyProfileActivity, userHashMap: HashMap<String, Any>) {
        fireStore.collection(Constants.USERS)
            .document(getCurrentUserId())
            .update(userHashMap)
            .addOnSuccessListener {
                Log.e(activity.javaClass.simpleName, "Profile Data updated successfully!")
                Toast.makeText(activity, "Profile updated successfully!", Toast.LENGTH_LONG).show()
                activity.profileUpdateSuccess()
            }.addOnFailureListener { exception ->
                activity.hideProgressDialog()
                Log.e(activity.javaClass.simpleName, "Error while creating a board.", exception)
                Toast.makeText(activity, "Error when updating the profile!", Toast.LENGTH_LONG)
                    .show()
            }
    }

    fun loadUserData(activity: Activity) {
        fireStore.collection(Constants.USERS)
            .document(getCurrentUserId())
            .get()
            .addOnSuccessListener { document ->
                val loggedInUser = document.toObject(User::class.java)!!

                when (activity) {
                    is SignInActivity -> activity.signInSuccess(loggedInUser)
                    is MainActivity -> activity.updateNavigationUserDetails(loggedInUser)
                    is MyProfileActivity -> activity.setUserDataInUI(loggedInUser)
                }
            }
            .addOnFailureListener { e ->
                when (activity) {
                    is SignInActivity -> activity.hideProgressDialog()
                    is MainActivity -> activity.hideProgressDialog()
                }
                Log.e("SignInUser", "Error writing document", e)
            }
    }

    fun getCurrentUserId(): String {
        val currentUser = FirebaseAuth.getInstance().currentUser
        var currentUserID = ""
        if (currentUser != null) {
            currentUserID = currentUser.uid
        }
        return currentUserID
    }
}