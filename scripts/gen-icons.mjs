import sharp from 'sharp';
import { readFileSync } from 'node:fs';
import { mkdirSync } from 'node:fs';
import { dirname, resolve } from 'node:path';

const root = resolve(import.meta.dirname, '..');
const iconSvg = readFileSync(resolve(root, 'resources/icon.svg'));
const splashSvg = readFileSync(resolve(root, 'resources/splash.svg'));

const out = [
  { svg: iconSvg, size: 1024, file: 'resources/icon.png' },
  { svg: splashSvg, size: 2732, file: 'resources/splash.png' },
  { svg: iconSvg, size: 192, file: 'www/icon-192.png' },
  { svg: iconSvg, size: 512, file: 'www/icon-512.png' },
  { svg: iconSvg, size: 180, file: 'www/apple-touch-icon.png' },
  { svg: iconSvg, size: 32, file: 'www/favicon.png' },
];

for (const o of out) {
  const dest = resolve(root, o.file);
  mkdirSync(dirname(dest), { recursive: true });
  await sharp(o.svg, { density: 384 })
    .resize(o.size, o.size, { fit: 'cover' })
    .png()
    .toFile(dest);
  console.log('wrote', o.file, `${o.size}x${o.size}`);
}
