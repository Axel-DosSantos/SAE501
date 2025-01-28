package com.example.myapplication.uiux

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.myapplication.model.UserViewModel

@Composable
fun QuestionnaireScreen(navController: NavHostController, userViewModel: UserViewModel = viewModel()) {
    var name by remember { mutableStateOf("") }
    var selectedOption by remember { mutableStateOf("") }
    var isNameValid by remember { mutableStateOf(false) }
    var isOptionSelected by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFEA5F)) // Changer le fond en jaune
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Contenu au milieu
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Texte "Bienvenue au questionnaire !" en grand et centré
            Text(
                text = "Bienvenue au questionnaire !",
                color = Color.Black,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Ajouter un champ de texte pour saisir un nom
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, RoundedCornerShape(50.dp))
                    .padding(10.dp)
            ) {
                BasicTextField(
                    value = name,
                    onValueChange = {
                        name = it
                        isNameValid = it.isNotBlank()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
                    visualTransformation = VisualTransformation.None,
                    singleLine = true,
                    decorationBox = { innerTextField ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White, RoundedCornerShape(50.dp))
                                .padding(10.dp),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            if (name.isEmpty()) {
                                Text("Saisir un nom", color = Color.Gray)
                            }
                            innerTextField()
                        }
                    }
                )
            }

            if (!isNameValid) {
                Text(
                    text = "Veuillez saisir un nom.",
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Ajouter une question sur le but de la démarche avec des RadioButtons
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, RoundedCornerShape(25.dp))
                    .padding(10.dp)
            ) {
                Column {
                    Text(
                        text = "Quel est le but de votre démarche ?",
                        color = Color.Black,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    // Ajouter des RadioButtons pour choisir entre plusieurs options
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(bottom = 8.dp)
                        ) {
                            RadioButton(
                                selected = selectedOption == "Prendre du poids",
                                onClick = {
                                    selectedOption = "Prendre du poids"
                                    isOptionSelected = true
                                },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = Color.Black, // Changer la couleur du bouton radio lorsqu'il est sélectionné
                                    unselectedColor = Color.Black // Changer la couleur du bouton radio lorsqu'il n'est pas sélectionné
                                )
                            )
                            Text("Prendre du poids", color = Color.Black)
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(bottom = 8.dp)
                        ) {
                            RadioButton(
                                selected = selectedOption == "Perdre du poids",
                                onClick = {
                                    selectedOption = "Perdre du poids"
                                    isOptionSelected = true
                                },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = Color.Black, // Changer la couleur du bouton radio lorsqu'il est sélectionné
                                    unselectedColor = Color.Black // Changer la couleur du bouton radio lorsqu'il n'est pas sélectionné
                                )
                            )
                            Text("Perdre du poids", color = Color.Black)
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(bottom = 8.dp)
                        ) {
                            RadioButton(
                                selected = selectedOption == "Maintenir le poids",
                                onClick = {
                                    selectedOption = "Maintenir le poids"
                                    isOptionSelected = true
                                },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = Color.Black, // Changer la couleur du bouton radio lorsqu'il est sélectionné
                                    unselectedColor = Color.Black // Changer la couleur du bouton radio lorsqu'il n'est pas sélectionné
                                )
                            )
                            Text("Maintenir le poids", color = Color.Black)
                        }
                    }

                    if (!isOptionSelected) {
                        Text(
                            text = "Veuillez sélectionner une option.",
                            color = Color.Red,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
            }
        }

        // Bouton pour terminer le questionnaire et naviguer vers la page des informations supplémentaires
        Button(
            onClick = {
                if (isNameValid && isOptionSelected) {
                    // Vous pouvez stocker les réponses ici si nécessaire
                    userViewModel.userName = name
                    navController.navigate("additionalInfo")
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black, // Changer la couleur du bouton en noir
                contentColor = Color.White // Changer la couleur du texte du bouton en blanc
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = 16.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Text("Terminer", fontSize = 18.sp)
        }
    }
}
