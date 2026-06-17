---
name: OceanFit
colors:
  surface: '#f7f9fb'
  surface-dim: '#d8dadc'
  surface-bright: '#f7f9fb'
  surface-container-lowest: '#ffffff'
  surface-container-low: '#f2f4f6'
  surface-container: '#eceef0'
  surface-container-high: '#e6e8ea'
  surface-container-highest: '#e0e3e5'
  on-surface: '#191c1e'
  on-surface-variant: '#434654'
  inverse-surface: '#2d3133'
  inverse-on-surface: '#eff1f3'
  outline: '#737685'
  outline-variant: '#c3c6d6'
  surface-tint: '#0c56d0'
  primary: '#003d9b'
  on-primary: '#ffffff'
  primary-container: '#0052cc'
  on-primary-container: '#c4d2ff'
  inverse-primary: '#b2c5ff'
  secondary: '#00696f'
  on-secondary: '#ffffff'
  secondary-container: '#00f1fe'
  on-secondary-container: '#006a70'
  tertiary: '#29436b'
  on-tertiary: '#ffffff'
  tertiary-container: '#425b84'
  on-tertiary-container: '#bcd3ff'
  error: '#ba1a1a'
  on-error: '#ffffff'
  error-container: '#ffdad6'
  on-error-container: '#93000a'
  primary-fixed: '#dae2ff'
  primary-fixed-dim: '#b2c5ff'
  on-primary-fixed: '#001848'
  on-primary-fixed-variant: '#0040a2'
  secondary-fixed: '#74f5ff'
  secondary-fixed-dim: '#00dbe7'
  on-secondary-fixed: '#002022'
  on-secondary-fixed-variant: '#004f54'
  tertiary-fixed: '#d6e3ff'
  tertiary-fixed-dim: '#aec7f6'
  on-tertiary-fixed: '#001b3d'
  on-tertiary-fixed-variant: '#2d476f'
  background: '#f7f9fb'
  on-background: '#191c1e'
  surface-variant: '#e0e3e5'
typography:
  display-lg:
    fontFamily: Sora
    fontSize: 48px
    fontWeight: '800'
    lineHeight: 56px
    letterSpacing: -0.02em
  headline-lg:
    fontFamily: Sora
    fontSize: 32px
    fontWeight: '700'
    lineHeight: 40px
    letterSpacing: -0.01em
  headline-lg-mobile:
    fontFamily: Sora
    fontSize: 28px
    fontWeight: '700'
    lineHeight: 36px
  headline-md:
    fontFamily: Sora
    fontSize: 24px
    fontWeight: '600'
    lineHeight: 32px
  body-lg:
    fontFamily: Hanken Grotesk
    fontSize: 18px
    fontWeight: '400'
    lineHeight: 28px
  body-md:
    fontFamily: Hanken Grotesk
    fontSize: 16px
    fontWeight: '400'
    lineHeight: 24px
  label-md:
    fontFamily: Hanken Grotesk
    fontSize: 14px
    fontWeight: '600'
    lineHeight: 20px
    letterSpacing: 0.05em
  data-display:
    fontFamily: Sora
    fontSize: 24px
    fontWeight: '800'
    lineHeight: 24px
    letterSpacing: -0.02em
rounded:
  sm: 0.25rem
  DEFAULT: 0.5rem
  md: 0.75rem
  lg: 1rem
  xl: 1.5rem
  full: 9999px
spacing:
  base: 8px
  container-margin: 20px
  gutter: 16px
  stack-sm: 4px
  stack-md: 12px
  stack-lg: 24px
  section-gap: 40px
---

## Brand & Style

The design system is engineered to evoke a sense of "Fluid Performance." It targets health-conscious individuals who value precision, intelligence, and a refreshing user experience. The aesthetic balances the high-energy requirements of a fitness application with the calming, structured nature of deep-sea environments.

The style is **Modern and Data-Driven**, utilizing a mix of high-contrast typography for readability during intense physical activity and subtle **Glassmorphism** to represent water-like transparency and depth. The UI should feel responsive and "living," with transitions that mimic the flow of water—smooth, intentional, and powerful.

Key principles:
- **Clarity under pressure:** High contrast and large hit targets for mid-workout use.
- **Intelligent depth:** Use of layers to separate raw data from AI-driven insights.
- **Vibrant Professionalism:** A palette that feels premium yet energetic.

## Colors

The palette is anchored by **Deep Sea Blue** (Primary), providing a stable, professional foundation. **Electric Turquoise** (Secondary) is used exclusively for action-oriented elements, progress indicators, and "active" states to provide a motivating visual pop. 

**Abyssal Navy** (Tertiary) provides high-contrast grounding for typography and dark-mode surfaces, while **Frosted White** (Neutral) ensures the UI remains airy and refreshing. 

Functional colors (Success, Warning, Error) should be tinted with a hint of blue to maintain harmony with the aquatic theme, ensuring they don't feel disconnected from the primary brand identity.

## Typography

This design system utilizes **Sora** for headlines and data visualizations. Its geometric yet open structure provides the "energetic" feel required for a fitness brand while maintaining a technical, intelligent edge. For body text and labels, **Hanken Grotesk** is chosen for its exceptional legibility and contemporary finish.

**Special Guidance:**
- **Numerical Data:** Use `data-display` (Sora ExtraBold) for all primary metrics (e.g., heart rate, calories).
- **All Caps:** Use `label-md` in all caps for secondary headers or category tags to create a clear architectural hierarchy.
- **Hierarchy:** Maintain generous vertical rhythm between headlines and body text to prevent visual clutter in data-heavy screens.

## Layout & Spacing

The layout follows a **4-column fluid grid** for mobile devices with a 20px safe-margin on either side. A strict **8px base unit** governs all spacing decisions to ensure a predictable, rhythmic UI.

- **Activity Cards:** Should span the full width of the 4-column grid or be paired in 2-column sets for secondary metrics.
- **Vertical Rhythm:** Use `section-gap` (40px) to separate distinct workout phases or daily summaries.
- **Touch Targets:** All interactive elements must maintain a minimum height of 48px to accommodate users with sweaty or moving hands.

## Elevation & Depth

To achieve the "intelligent and responsive" vibe, this design system moves away from traditional heavy shadows in favor of **Tonal Layers** and **Tinted Ambient Glows**.

- **Surface Level 0:** The neutral background (Frosted White).
- **Surface Level 1 (Cards):** Pure white with a very soft, diffused `#002147` shadow (4% opacity) and a 1px border in a light turquoise tint.
- **Surface Level 2 (Modals/Overlays):** Glassmorphic surfaces using a `blur(12px)` backdrop filter and a semi-transparent white fill (80%).
- **AI Badges:** Use a subtle outer glow using the Secondary Turquoise color to signify "active intelligence."

## Shapes

The shape language is **Rounded (Level 2)**. This strikes the balance between the approachability of a lifestyle app and the precision of a professional tool. 

- **Standard Elements (Buttons, Inputs):** 0.5rem (8px) corner radius.
- **Containers (Cards, Activity Blocks):** 1rem (16px) corner radius.
- **Progress Bars:** Fully rounded (pill-shaped) to represent the fluid motion of the "OceanFit" brand.
- **AI Insight Badges:** Use a slightly larger `rounded-xl` (24px) to distinguish them from standard functional buttons.

## Components

### Workout Action Buttons
Primary buttons use a solid Deep Sea Blue fill with white Sora Bold text. The "Start Workout" or "Finish" buttons should utilize a subtle horizontal gradient from Primary to Secondary to draw maximum attention.

### Activity Metric Cards
Cards must prioritize the `data-display` typography. The label (e.g., "BPM") should be placed either directly below or to the right of the value in `label-md` Abyssal Navy. Use a small sparkline or icon in the top right corner to show trend direction.

### Progress Bars
Progress bars are dual-tone. The unfilled track is a very pale turquoise (10% opacity), while the filled portion is a vibrant gradient of Secondary Turquoise. For "Over-achievement" states, the bar should pulse with a subtle glow.

### AI Recommendation Badges
These are small, floating elements with a backdrop-blur. They feature a "Sparkle" icon and use the Secondary Turquoise for text. These badges should feel "light" as if they are floating above the main content.

### Input Fields
Inputs are minimal: a 1px bottom border in Deep Sea Blue that transitions to a 2px Secondary Turquoise border on focus. Labels should float above the field in `label-md`.

### List Items
Workout history lists should use a clean, single-line separator. Each item includes a leading icon in a rounded square container and a trailing chevron for navigation.