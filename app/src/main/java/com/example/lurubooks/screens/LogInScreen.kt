package com.example.lurubooks.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ContentScale.Companion.Fit
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.lurubooks.R
import com.example.lurubooks.db.entities.UsersEntity
import com.example.lurubooks.viewModels.UserViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.launch

@Composable
fun LogInScreen(
    modifier: Modifier = Modifier, navController: NavController, userViewModel: UserViewModel
) {
    val coroutineScope = rememberCoroutineScope()
    var auth = Firebase.auth
    var correo by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val patronCorreo = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    val context = LocalContext.current
    Scaffold(
        modifier = Modifier.fillMaxSize(), containerColor = Color(57, 86, 125, 255)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Image(
                painter = painterResource(id = R.drawable.imagenprincipal),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(200.dp)
                    .clip(CircleShape),
                alignment = Alignment.Center,
                contentScale = ContentScale.FillBounds
            )
            Spacer(
                modifier = Modifier.size(50.dp)
            )
            Text(
                text = "Correo Electronico",
                fontSize = 20.sp,
                color = Color.White,
                fontFamily = FontFamily.Serif,
            )
            OutlinedTextField(
                value = correo,
                onValueChange = { correo = it },
                label = { Text("Correo Electronico", color = Color.LightGray) },
                colors = OutlinedTextFieldDefaults.colors().copy(
                    focusedTextColor = Color.White,
                    cursorColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedIndicatorColor = Color(207, 185, 165, 255)
                ),
                singleLine = true,
                modifier = Modifier.fillMaxWidth(0.7f)
            )

            Spacer(modifier = Modifier.size(30.dp))

            Text(
                text = "Contraseña",
                fontSize = 20.sp,
                color = Color.White,
                fontFamily = FontFamily.Serif,
            )
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña", color = Color.LightGray) },
                visualTransformation = PasswordVisualTransformation(),
                colors = OutlinedTextFieldDefaults.colors().copy(
                    focusedTextColor = Color.White,
                    cursorColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedIndicatorColor = Color(207, 185, 165, 255),
                ),
                singleLine = true,
                modifier = Modifier.fillMaxWidth(0.7f)
            )

            Spacer(modifier = Modifier.size(40.dp))

            Row {
                Button(
                    onClick = {
                        if (correo.matches(patronCorreo.toRegex()) && password.length >= 6) {
                            auth.signInWithEmailAndPassword(correo, password)
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        coroutineScope.launch {
                                            val user = auth.currentUser
                                            if (user != null) {
                                                val existingUser = userViewModel.getUserById(user.uid.hashCode())
                                                if (existingUser == null) {
                                                    val newUser = UsersEntity(
                                                        id = user.uid.hashCode(),
                                                        email = correo,
                                                        password = password
                                                    )
                                                    userViewModel.insertUser(newUser)
                                                }
                                                Toast.makeText(
                                                    context, "Usuario logeado", Toast.LENGTH_SHORT
                                                ).show()
                                                navController.navigate("principal")
                                            }
                                        }

                                    } else {
                                        Toast.makeText(
                                            context, "Error al iniciar sesion", Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                        } else {
                            Toast.makeText(context, "Correo invalido", Toast.LENGTH_SHORT).show()
                        }

                    }, colors = ButtonDefaults.buttonColors().copy(
                        containerColor = Color(207, 185, 165, 255)
                    )
                ) {
                    Text("Iniciar Sesion", color = Color.DarkGray)
                }

                Spacer(modifier = Modifier.size(20.dp))

                Button(
                    onClick = {
                        if (correo.matches(patronCorreo.toRegex()) && password.length >= 6) {
                            auth.createUserWithEmailAndPassword(correo, password)
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        coroutineScope.launch {
                                            val user = auth.currentUser
                                            if (user != null) {
                                                val newUser = UsersEntity(
                                                    id = user.uid.hashCode(),
                                                    email = correo,
                                                    password = password
                                                )
                                                userViewModel.insertUser(newUser)
                                            }
                                        }
                                        Toast.makeText(
                                            context, "Usuario creado", Toast.LENGTH_SHORT
                                        ).show()
                                        navController.navigate("principal")
                                    } else {
                                        Toast.makeText(
                                            context, "Error al crear usuario", Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                        } else {
                            Toast.makeText(context, "Correo invalido", Toast.LENGTH_SHORT).show()
                        }

                    }, colors = ButtonDefaults.buttonColors().copy(
                        containerColor = Color(207, 185, 165, 255)
                    )
                ) {
                    Text("Registrarse", color = Color.DarkGray)
                }

            }
            Spacer(modifier = Modifier.size(150.dp))
        }
    }

}