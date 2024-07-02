package com.aliberkaygedikoglu.finalproject

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import com.aliberkaygedikoglu.finalproject.model.User
import com.aliberkaygedikoglu.finalproject.model.UserLogin
import com.aliberkaygedikoglu.finalproject.retrofit.ApiClient
import com.aliberkaygedikoglu.finalproject.retrofit.DummyService
import com.aliberkaygedikoglu.finalproject.ui.theme.Dark
import com.aliberkaygedikoglu.finalproject.ui.theme.GreyBlue
import com.aliberkaygedikoglu.finalproject.ui.theme.GreyBlue1
import com.aliberkaygedikoglu.finalproject.ui.theme.GreyBlue2
import com.aliberkaygedikoglu.finalproject.ui.theme.GreyBlue3
import com.aliberkaygedikoglu.finalproject.ui.theme.Purple
import com.aliberkaygedikoglu.finalproject.ui.theme.White
import com.aliberkaygedikoglu.finalproject.ui.theme.Yellow
import com.aliberkaygedikoglu.finalproject.ui.theme.bebasNeueFontFamily
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Preview(showBackground = true)
@Composable
fun Login(
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current
) {
    val backgroundImage = painterResource(R.drawable.shopping_bg)

    var emailText by remember { mutableStateOf("") }
    var passwordText by remember { mutableStateOf("") }
    val rememberMeState = remember { mutableStateOf(false) }

    Box(
        modifier = modifier.fillMaxSize(),
    ) {

        Image(
            modifier = Modifier.fillMaxWidth(),
            painter = backgroundImage,
            contentDescription = null,
           // contentScale = ContentScale.Crop
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 150.dp)
                .background(GreyBlue, shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .padding(horizontal = 35.dp)
                    .offset(y = (-55).dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(115.dp)
                        .background(Yellow, shape = CircleShape)
                        .border(2.dp, White, CircleShape).padding(0.dp,0.dp,10.dp,0.dp),

                    contentAlignment = Alignment.Center
                ) {
                    Icon(

                        painter = painterResource(id = R.drawable.shopping_cart),
                        tint = White,
                        contentDescription = "Logo",
                        modifier = Modifier.size(80.dp)
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    "LOGIN",
                    fontSize = 56.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = White,
                    lineHeight = 40.sp,
                    fontFamily = bebasNeueFontFamily,
                    letterSpacing = 0.07.em
                )

                Text(
                    "Explore the best",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = White,
                    lineHeight = 40.sp,
                    modifier = Modifier.padding(5.dp)
                )

                Column {


                    OutlinedTextField(
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Person,
                                tint = White,
                                contentDescription = null
                            )
                        },

                        value = emailText,
                        onValueChange = { emailText = it },
                        label = { Text(text = "Username", color = White) },

                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp),
                        shape = RoundedCornerShape(50.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            cursorColor = White,
                            focusedTextColor = White,
                            focusedSupportingTextColor = White,
                            unfocusedTextColor = White,
                            focusedBorderColor = White,
                            unfocusedBorderColor = White,
                            focusedLabelColor = White

                        ),

                        singleLine = true,
                    )
                }



                Spacer(modifier = Modifier.height(20.dp))

                Column {


                    OutlinedTextField(

                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                tint = White,
                                contentDescription = null
                            )
                        },

                        value = passwordText,
                        onValueChange = { passwordText = it },
                        label = { Text(text = "Password",color = White) },
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp),
                        shape = RoundedCornerShape(50.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            cursorColor = White,
                            focusedTextColor = White,
                            focusedSupportingTextColor = White,
                            unfocusedTextColor = White,
                            focusedBorderColor = White,
                            unfocusedBorderColor = White,
                            focusedLabelColor = White
                        ),

                        singleLine = true,
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                }

                Spacer(modifier = Modifier.height(20.dp))


                Button(

                    onClick = {
                        val loginUser = UserLogin(emailText, passwordText)
                        val dummyService = ApiClient.getClient().create(DummyService::class.java)
                        dummyService.login(loginUser).enqueue(object : Callback<User> {
                            override fun onResponse(call: Call<User>, response: Response<User>) {
                                if (response.isSuccessful){
                                    val intent = Intent(context,MainActivity::class.java)
                                    intent.putExtra("user",response.body())
                                    context.startActivity(intent)
                                    val activity = context as Activity
                                    activity.finish()
                                }else{
                                    Log.e("err","giris hatası")
                                }
                            }

                            override fun onFailure(call: Call<User>, t: Throwable) {
                                Log.e("err","servis hatası")
                            }
                        })


                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Yellow
                    ),
                    shape = RoundedCornerShape(50.dp)
                ) {
                    Text(
                        "Login",
                        fontSize = 18.sp,
                        color = White
                    )
                }

                Spacer(modifier = Modifier.weight(1f))


            }


        }
    }
}



