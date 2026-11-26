# Malio branding

Malio is an annotation-based Java object validation framework. Its mongoose mascot holds a
magnifying glass containing a turquoise checkmark. The mongoose represents actively
finding errors and verifying inputs. Its silhouette, rounded lowercase wordmark and colors follow the Paniolo,
Pololei and GWT Spring Boot Archetype branding family.

## Files

| File | Use |
| --- | --- |
| `malio-banner.svg` / `.png` | Horizontal logo for light backgrounds, including a README |
| `malio-banner-dark.svg` / `.png` | Horizontal logo for dark backgrounds |
| `malio-logo.svg` / `.png` | Horizontal logo for light backgrounds, compact PNG |
| `malio-logo-dark.svg` / `.png` | Horizontal logo for dark backgrounds, compact PNG |
| `malio-icon.svg` / `.png` | Mongoose mascot on a square canvas, light backgrounds |
| `malio-icon-dark.svg` / `.png` | Mongoose mascot on a square canvas, dark backgrounds |
| `malio-mark.svg` / `.png` | Small m mark with a turquoise dot |
| `malio-mark-dark.svg` / `.png` | Small mark for dark backgrounds |
| `malio-favicon.svg` | Pale m and turquoise dot on a rounded navy tile |
| `malio-favicon-32.png` | Browser favicon, 32 × 32 px |
| `malio-favicon-180.png` | Apple touch icon, 180 × 180 px |
| `malio-favicon-512.png` | App icon, 512 × 512 px |
| `favicon.ico` | Browser favicon containing 16, 32 and 48 px images |

Both logo and banner place the mascot on the left and the wordmark on the right.
There is no stacked layout in this package.

## Colors and construction

- Navy: `#01244F`
- Turquoise: `#019ABC`
- Pale blue for dark backgrounds: `#E2ECF5`

All SVGs contain real vector paths and circles with solid fills in exactly these colors.
They contain no embedded bitmap images, external fonts, resources, filters or gradients.
The mascot and wordmark are cleaned and traced to smooth outlines; the i dot is rebuilt
as a circle. The small mark uses the wordmark's m with a matching turquoise dot.
The original concept is kept outside this repository.

Logo, banner, icon and mark backgrounds are transparent. Dark variants replace navy with
pale blue and retain turquoise. Favicons have a navy tile with transparent corners. Use
the small m mark at 16–32 px; the complete mongoose is intended for larger sizes.

PNGs are rendered from the matching SVGs: banner 900 × 204 px, horizontal logo 640 × 145 px,
icon 256 × 256 px and mark 512 × 512 px. Visible pixels use only the stated brand colors;
edge smoothing is represented with partial alpha.

## README usage

```html
<p align="center">
  <picture>
    <source media="(prefers-color-scheme: dark)" srcset="docs/branding/malio-banner-dark.svg">
    <img src="docs/branding/malio-banner.svg" alt="Malio" width="480">
  </picture>
</p>
```

## Favicons

When the files are served from the same directory as the page:

```html
<link rel="icon" href="malio-favicon.svg" type="image/svg+xml">
<link rel="icon" href="favicon.ico" sizes="16x16 32x32 48x48">
<link rel="apple-touch-icon" href="malio-favicon-180.png">
```

## Use of the logo

This logo identifies the NaluKit Malio project. As with the Paniolo branding, the logo is
not offered under the code's Apache License 2.0. Use it unchanged to refer to Malio; do
not use it to identify other products or imply endorsement by NaluKit or Frank Hossfeld.
