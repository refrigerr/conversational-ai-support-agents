# Integration Guide

## Authentication
Use API Key:
Authorization: Bearer <API_KEY>

## Data Flow
Client -> API -> Service -> Response

## Webhooks
POST /api/v1/webhooks

Payload:
{
  "event": "resource.created",
  "data": {
    "id": "123"
  }
}

## Errors
- 400 Bad Request
- 401 Unauthorized
- 500 Server Error
