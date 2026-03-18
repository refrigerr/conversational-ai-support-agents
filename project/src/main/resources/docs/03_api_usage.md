# API Usage Guide

## Base URL
https://api.example.com/v1

## Endpoints

### Get Resource
GET /resources/{id}

### Create Resource
POST /resources
{
  "name": "New Resource"
}

## Pagination
GET /resources?page=1&limit=20

## Rate Limits
100 requests per minute

## Best Practices
- Cache responses
- Validate inputs
