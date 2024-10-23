package com.example.feature.home.presentation.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.common.designsystem.R
import com.example.common.designsystem.dimens.Dimens.default
import com.example.common.designsystem.dimens.Dimens.defaultAlt
import com.example.common.designsystem.dimens.Dimens.small
import com.example.common.designsystem.dimens.responsiveSp
import com.example.common.designsystem.theme.designSystemThemePalette
import com.example.common.designsystem.theme.mavenProFontFamily

@Composable
fun CustomDialog(
    onCancelClicked: () -> Unit,
    onContinueClicked: () -> Unit,
) {

    Dialog(onDismissRequest = {}) {
        Box(
            modifier = Modifier
                .background(
                    designSystemThemePalette.surfaceColor,
                    shape = RoundedCornerShape(small)
                )
                .padding(default)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = stringResource(id = R.string.dialog_description),
                        color = designSystemThemePalette.onSurfaceColor,
                        fontSize = 20.sp.responsiveSp(),
                        fontFamily = mavenProFontFamily,
                        fontWeight = FontWeight.SemiBold,
                    )
                }
                Spacer(modifier = Modifier.height(defaultAlt))
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(
                        onClick = onCancelClicked,
                        modifier = Modifier.background(designSystemThemePalette.surfaceColor)
                    ) {
                        Text(
                            text = stringResource(R.string.dialog_cancel_button),
                            color = designSystemThemePalette.onSurfaceColor
                        )
                    }
                    Spacer(modifier = Modifier.width(small))
                    Button(
                        onClick = onContinueClicked,
                        colors = ButtonDefaults.buttonColors(backgroundColor = designSystemThemePalette.onSurfaceColor)
                    ) {
                        Text(
                            text = stringResource(R.string.dialog_delete_button),
                            color = designSystemThemePalette.surfaceColor
                        )
                    }
                }
            }
        }
    }
}