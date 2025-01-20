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
import androidx.navigation.NavHostController

@Composable
fun AdditionalInfoScreen(navController: NavHostController) {
    var height by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var isHeightValid by remember { mutableStateOf(false) }
    var isWeightValid by remember { mutableStateOf(false) }
    var isGenderSelected by remember { mutableStateOf(false) }

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
            // Texte "Informations supplémentaires" en grand et centré
            Text(
                text = "Informations supplémentaires",
                color = Color.Black,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Ajouter un champ de texte pour saisir la taille
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, RoundedCornerShape(50.dp))
                    .padding(10.dp)
            ) {
                BasicTextField(
                    value = height,
                    onValueChange = {
                        height = it
                        isHeightValid = it.isNotBlank()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
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
                            if (height.isEmpty()) {
                                Text("Saisir la taille (en cm)", color = Color.Gray)
                            }
                            innerTextField()
                        }
                    }
                )
            }

            if (!isHeightValid) {
                Text(
                    text = "Veuillez saisir votre taille.",
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Ajouter un champ de texte pour saisir le poids actuel
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, RoundedCornerShape(50.dp))
                    .padding(10.dp)
            ) {
                BasicTextField(
                    value = weight,
                    onValueChange = {
                        weight = it
                        isWeightValid = it.isNotBlank()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
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
                            if (weight.isEmpty()) {
                                Text("Saisir le poids actuel (en kg)", color = Color.Gray)
                            }
                            innerTextField()
                        }
                    }
                )
            }

            if (!isWeightValid) {
                Text(
                    text = "Veuillez saisir votre poids actuel.",
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Ajouter une question sur le genre avec des RadioButtons
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, RoundedCornerShape(25.dp))
                    .padding(10.dp)
            ) {
                Column {
                    Text(
                        text = "Quel est votre genre ?",
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
                                selected = gender == "Homme",
                                onClick = {
                                    gender = "Homme"
                                    isGenderSelected = true
                                },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = Color.Black, // Changer la couleur du bouton radio lorsqu'il est sélectionné
                                    unselectedColor = Color.Black // Changer la couleur du bouton radio lorsqu'il n'est pas sélectionné
                                )
                            )
                            Text("Homme", color = Color.Black)
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(bottom = 8.dp)
                        ) {
                            RadioButton(
                                selected = gender == "Femme",
                                onClick = {
                                    gender = "Femme"
                                    isGenderSelected = true
                                },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = Color.Black, // Changer la couleur du bouton radio lorsqu'il est sélectionné
                                    unselectedColor = Color.Black // Changer la couleur du bouton radio lorsqu'il n'est pas sélectionné
                                )
                            )
                            Text("Femme", color = Color.Black)
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(bottom = 8.dp)
                        ) {
                            RadioButton(
                                selected = gender == "Autre",
                                onClick = {
                                    gender = "Autre"
                                    isGenderSelected = true
                                },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = Color.Black, // Changer la couleur du bouton radio lorsqu'il est sélectionné
                                    unselectedColor = Color.Black // Changer la couleur du bouton radio lorsqu'il n'est pas sélectionné
                                )
                            )
                            Text("Autre", color = Color.Black)
                        }
                    }

                    if (!isGenderSelected) {
                        Text(
                            text = "Veuillez sélectionner votre genre.",
                            color = Color.Red,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
            }
        }

        // Bouton pour terminer la saisie des informations supplémentaires et naviguer vers l'écran principal
        Button(
            onClick = {
                if (isHeightValid && isWeightValid && isGenderSelected) {
                    // Vous pouvez stocker les réponses ici si nécessaire
                    navController.navigate("main")
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
