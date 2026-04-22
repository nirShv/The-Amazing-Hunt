package com.theamazinghunt.presentation.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.theamazinghunt.R
import com.theamazinghunt.domain.model.GameStatus
import com.theamazinghunt.domain.model.TreasureHuntGame

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun GameSummaryCard(
    game: TreasureHuntGame,
    onClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    if (onClick == null) {
        Card(modifier = modifier.fillMaxWidth()) {
            GameSummaryCardContent(game = game)
        }
    } else {
        Card(
            onClick = onClick,
            modifier = modifier.fillMaxWidth()
        ) {
            GameSummaryCardContent(game = game)
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun GameSummaryCardContent(game: TreasureHuntGame) {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = game.title,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = stringResource(
                        R.string.game_card_duration,
                        game.estimatedDurationMinutes
                    ),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            StatusBadge(status = game.status)
        }

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            MetadataPill(text = stringResource(R.string.game_card_players, game.playersCount))
            MetadataPill(
                text = stringResource(
                    R.string.game_card_points,
                    game.pointsOfInterest.size
                )
            )
            MetadataPill(text = stringResource(R.string.game_card_clues, game.clues.size))
        }
    }
}

@Composable
private fun StatusBadge(status: GameStatus) {
    Surface(
        shape = MaterialTheme.shapes.small,
        color = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
    ) {
        Text(
            text = stringResource(status.labelRes),
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
            style = MaterialTheme.typography.labelMedium
        )
    }
}

@Composable
private fun MetadataPill(text: String) {
    Surface(
        shape = MaterialTheme.shapes.small,
        color = MaterialTheme.colorScheme.surfaceVariant,
        contentColor = MaterialTheme.colorScheme.onSurfaceVariant
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
            style = MaterialTheme.typography.labelMedium
        )
    }
}

private val GameStatus.labelRes: Int
    @StringRes get() = when (this) {
        GameStatus.DRAFT -> R.string.game_status_draft
        GameStatus.READY -> R.string.game_status_ready
        GameStatus.COMPLETED -> R.string.game_status_completed
    }
