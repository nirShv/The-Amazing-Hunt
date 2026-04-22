package com.theamazinghunt.presentation.creategame

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.EmojiObjects
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.theamazinghunt.R

@Composable
fun CreateGameBasicsScreen(
    uiState: CreateGameUiState,
    onTitleChange: (String) -> Unit,
    onPlayersChange: (String) -> Unit,
    onDurationChange: (String) -> Unit,
    onSaveDraft: (String) -> Unit,
    onBack: () -> Unit,
    onNext: () -> Unit,
    modifier: Modifier = Modifier
) {
    val defaultTitle = stringResource(R.string.default_game_title)

    WizardScaffold(
        step = WizardStep.BASICS,
        onBack = onBack,
        modifier = modifier,
        footer = {
            WizardFooter(
                onPrevious = onBack,
                onNext = onNext,
                previousLabel = R.string.back,
                nextLabel = R.string.next
            )
        }
    ) {
        OutlinedTextField(
            value = uiState.title,
            onValueChange = onTitleChange,
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = stringResource(R.string.game_name_label)) },
            placeholder = { Text(text = stringResource(R.string.game_name_placeholder)) },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Edit, contentDescription = null)
            },
            singleLine = true
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = uiState.playersCount,
                onValueChange = onPlayersChange,
                modifier = Modifier.weight(1f),
                label = { Text(text = stringResource(R.string.players_count_label)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true
            )
            OutlinedTextField(
                value = uiState.durationMinutes,
                onValueChange = onDurationChange,
                modifier = Modifier.weight(1f),
                label = { Text(text = stringResource(R.string.duration_label)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true
            )
        }

        Button(
            onClick = { onSaveDraft(defaultTitle) },
            enabled = !uiState.isSaving,
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(vertical = 14.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Save,
                contentDescription = stringResource(R.string.content_description_save)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = stringResource(R.string.save_draft))
        }

        if (uiState.draftSaved) {
            Text(
                text = stringResource(R.string.draft_saved),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun CreateGamePlayAreaScreen(
    onBack: () -> Unit,
    onPrevious: () -> Unit,
    onNext: () -> Unit,
    modifier: Modifier = Modifier
) {
    PlaceholderStepScreen(
        step = WizardStep.PLAY_AREA,
        badges = listOf(
            IntegrationBadge(Icons.Default.LocationOn, R.string.maps_placeholder_badge)
        ),
        onBack = onBack,
        onPrevious = onPrevious,
        onNext = onNext,
        modifier = modifier
    )
}

@Composable
fun CreateGamePointsScreen(
    onBack: () -> Unit,
    onPrevious: () -> Unit,
    onNext: () -> Unit,
    modifier: Modifier = Modifier
) {
    PlaceholderStepScreen(
        step = WizardStep.POINTS,
        badges = listOf(
            IntegrationBadge(Icons.Default.Place, R.string.places_placeholder_badge)
        ),
        onBack = onBack,
        onPrevious = onPrevious,
        onNext = onNext,
        modifier = modifier
    )
}

@Composable
fun CreateGameCluesScreen(
    onBack: () -> Unit,
    onPrevious: () -> Unit,
    onNext: () -> Unit,
    modifier: Modifier = Modifier
) {
    PlaceholderStepScreen(
        step = WizardStep.CLUES,
        badges = listOf(
            IntegrationBadge(Icons.Default.EmojiObjects, R.string.ai_placeholder_badge)
        ),
        onBack = onBack,
        onPrevious = onPrevious,
        onNext = onNext,
        modifier = modifier
    )
}

@Composable
fun CreateGameReviewScreen(
    uiState: CreateGameUiState,
    onSaveDraft: (String) -> Unit,
    onBack: () -> Unit,
    onPrevious: () -> Unit,
    onFinish: () -> Unit,
    modifier: Modifier = Modifier
) {
    val defaultTitle = stringResource(R.string.default_game_title)

    WizardScaffold(
        step = WizardStep.REVIEW,
        onBack = onBack,
        modifier = modifier,
        footer = {
            WizardFooter(
                onPrevious = onPrevious,
                onNext = onFinish,
                previousLabel = R.string.previous,
                nextLabel = R.string.finish
            )
        }
    ) {
        IntegrationBadges(
            badges = listOf(
                IntegrationBadge(Icons.Default.PictureAsPdf, R.string.pdf_placeholder_badge),
                IntegrationBadge(Icons.Default.Check, R.string.coming_soon)
            )
        )

        Button(
            onClick = { onSaveDraft(defaultTitle) },
            enabled = !uiState.isSaving,
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(vertical = 14.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Save,
                contentDescription = stringResource(R.string.content_description_save)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = stringResource(R.string.save_draft))
        }

        if (uiState.draftSaved) {
            Text(
                text = stringResource(R.string.draft_saved),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
private fun PlaceholderStepScreen(
    step: WizardStep,
    badges: List<IntegrationBadge>,
    onBack: () -> Unit,
    onPrevious: () -> Unit,
    onNext: () -> Unit,
    modifier: Modifier = Modifier
) {
    WizardScaffold(
        step = step,
        onBack = onBack,
        modifier = modifier,
        footer = {
            WizardFooter(
                onPrevious = onPrevious,
                onNext = onNext,
                previousLabel = R.string.previous,
                nextLabel = R.string.next
            )
        }
    ) {
        IntegrationBadges(badges = badges)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun WizardScaffold(
    step: WizardStep,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    footer: @Composable () -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = stringResource(R.string.wizard_title)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.content_description_back)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = stringResource(
                    R.string.wizard_step_counter,
                    step.number,
                    WizardStep.totalSteps
                ),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary
            )
            LinearProgressIndicator(
                progress = { step.number / WizardStep.totalSteps.toFloat() },
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = stringResource(step.titleRes),
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = stringResource(step.bodyRes),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            content()
            Spacer(modifier = Modifier.height(8.dp))
            footer()
        }
    }
}

@Composable
private fun WizardFooter(
    onPrevious: () -> Unit,
    onNext: () -> Unit,
    @StringRes previousLabel: Int,
    @StringRes nextLabel: Int
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedButton(
            onClick = onPrevious,
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(vertical = 14.dp)
        ) {
            Text(text = stringResource(previousLabel))
        }
        Button(
            onClick = onNext,
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(vertical = 14.dp)
        ) {
            Text(text = stringResource(nextLabel))
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun IntegrationBadges(badges: List<IntegrationBadge>) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        badges.forEach { badge ->
            Surface(
                shape = MaterialTheme.shapes.small,
                color = MaterialTheme.colorScheme.tertiaryContainer,
                contentColor = MaterialTheme.colorScheme.onTertiaryContainer
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(imageVector = badge.icon, contentDescription = null)
                    Text(
                        text = stringResource(badge.labelRes),
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
        }
    }
}

private data class IntegrationBadge(
    val icon: ImageVector,
    @StringRes val labelRes: Int
)
