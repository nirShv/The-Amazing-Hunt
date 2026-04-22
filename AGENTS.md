# Codex Working Rules – The Amazing Hunt

## Source of truth
- Use docs/PRODUCT_SPEC.md as the product and technical source of truth.
- The current user prompt is the execution instruction.
- Do not implement the full product spec unless explicitly requested.

## Execution rule
- Implement only the specific phase requested in the current prompt.
- Stop after completing the requested phase.
- Do not start the next phase without explicit instruction.

## Architecture
- Keep the project split into presentation, domain, and data layers.
- Keep business logic out of Compose screens.
- Use ViewModels for screen state.
- Use repositories for data access.
- Use service interfaces for Maps, Places, route generation, content generation, and PDF generation.

## Localization
- Default UI language is Hebrew.
- All visible UI strings must be in strings.xml.
- Do not hardcode visible UI text in Compose.
- Keep the app ready for English localization.

## MVP constraints
- No backend.
- No user accounts.
- No cloud sync.
- No multiplayer.
- No GPS arrival validation.
- No QR validation.
- No payments.

## Verification
- After code changes, run assembleDebug when possible.
- After each task, summarize:
  1. What changed.
  2. Files created or updated.
  3. How to test manually.
  4. Whether assembleDebug passed.
