s=set()
import requests
from bs4 import BeautifulSoup # importing class(easily work with html contents)
url = 'https://redvinca.com/' # Fetch the web page
response = requests.get(url)   # receive full html code of that webpage
soup = BeautifulSoup(response.content, 'html.parser')  # break down content into components 
for link in soup.find_all('a'): # Find all <a> tags and print their href attributes
    href = link.get('href')
    if not href.startswith('#') and not href.startswith('mailto:') and not href.startswith('tel:'):
        if href.startswith('/') or href.startswith('http'):
            s.add(href)
print(s)