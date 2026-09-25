#!/usr/bin/env sh
# Copia los mapas compartidos (/shared_assets) a los assets de Flutter.
# Flutter no puede empaquetar archivos fuera de su carpeta, por eso necesita esta copia.
# Ejecútalo desde cualquier lugar cada vez que cambies algo en /shared_assets.
set -e
cd "$(dirname "$0")/.."
rm -rf assets
cp -r ../shared_assets assets
echo "Assets sincronizados desde ../shared_assets"
