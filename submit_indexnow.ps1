$key = "b76c1379e4fd4bc3b248edd642c9eaf2"
$url = "https://www.codefoxsoft.com/index.html"
$engine = "https://www.bing.com/indexnow"

$body = @{
    host = "www.codefoxsoft.com"
    key = $key
    keyLocation = "https://www.codefoxsoft.com/$key.txt"
    urlList = @(
        "https://www.codefoxsoft.com/",
        "https://www.codefoxsoft.com/index.html",
        "https://www.codefoxsoft.com/about.html",
        "https://www.codefoxsoft.com/blog.html",
        "https://www.codefoxsoft.com/aiblogpost.html",
        "https://www.codefoxsoft.com/privacy.html",
        "https://www.codefoxsoft.com/foxrundownload.html",
        "https://www.codefoxsoft.com/spacepongdownload.html",
        "https://www.codefoxsoft.com/alldownload.html",
        "https://www.codefoxsoft.com/betasearch.html",
        "https://www.codefoxsoft.com/launch.html",
        "https://www.codefoxsoft.com/thanks.html"
    )
}

$start = Get-Date
try {
    $response = Invoke-RestMethod -Uri $engine -Method Post -Body ($body | ConvertTo-Json) -ContentType "application/json" -ErrorAction Stop
    Write-Host "Success! IndexNow Response: $response"
} catch {
    Write-Host "Error submitting to IndexNow: $_"
    # Even if it errors, check if it's a 200/202 which might be caught if return is empty or weird
}
$end = Get-Date

Write-Host "Submitted in $(($end - $start).TotalSeconds) seconds"
