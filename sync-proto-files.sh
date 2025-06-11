#!/bin/bash

# Load environment variables
set -a
source protosync.env
set +a


aws --endpoint-url "https://${R2_ACCOUNT_ID}.${R2_ENDPOINT}" \
    s3 cp "s3://${PROTO_BUCKET}/${PROTO_VERSION}/protos.tar.gz" protos.tar.gz

mkdir -p "$OUT_DIR"

tar -xzf protos.tar.gz -C "$OUT_DIR"

rm protos.tar.gz

echo "✅ Protos extracted to $OUT_DIR"
