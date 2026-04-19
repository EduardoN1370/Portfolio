
# Splunk Queries 

In this project, I demonstrate my skills by performing basic queries in Splunk and analyzing the displayed information.

## Resources
For this project, information regarding cybersecurity attacks worldwide across various time periods was utilized, gathered in the `cybersecurity_attacks.csv` file attached to the repository.

## Questions 
This analysis aims to identify patterns, behaviors, and trends in attack activity. It focuses on understanding the origin, frequency, and types of attacks, as well as how systems responded to them. Additionally, it seeks to detect recurring malicious actors and evaluate how attack behavior changes over time. Overall, the goal is to gain insights that can improve threat detection and response strategies.

📊 Analysis Questions
- [ ] How many attacks were not blocked?
- [ ] What were the types of attacks, and how many of each were carried out?
- [ ] Show the countries of origin for the attackers' IP addresses.
- [ ] What are the top 5 countries with the highest number of attacks, including their cities?
- [ ] How many attacks were carried out per hour on the day with the highest number of attacks?
- [ ] Were there IP addresses that carried out attacks on different days? If so, which days?
- [ ] Did a single IP address generate different results when launching multiple attacks?
- [ ] What were the responses to each type of attack?
- [ ] What is the most common type of attack by country?

## Solucion
### How many attacks were not blocked? 

```
source="cybersecurity_attacks.csv"  sourcetype="csv" "Action Taken"!=Blocked | stats count by "Action Taken”
```

<img width="2539" height="367" alt="image" src="https://github.com/user-attachments/assets/8aa334a0-37ad-45ca-9b6c-031a794ae2a7" />

### What were the types of attacks, and how many of each were carried out?
```
source="cybersecurity_attacks.csv" sourcetype="csv"   |stats count by "Attack Type"
```
<img width="2557" height="393" alt="image" src="https://github.com/user-attachments/assets/3adf743c-63eb-4bf9-91f8-6c14672acb32" />
<img width="610" height="358" alt="image" src="https://github.com/user-attachments/assets/5654d1ad-c59b-4333-a9c8-81a8cdc5cd0f" />

### Show the countries of origin for the attackers' IP addresses.

List of some countries.
```
source="cybersecurity_attacks.csv"  sourcetype="csv"  | iplocation "Source IP Address"  | stats  values(Country) 
```
<img width="424" height="1201" alt="image" src="https://github.com/user-attachments/assets/b8fa39a1-2b1d-4a38-b26b-62f602993052" />


Representation

```
source="cybersecurity_attacks.csv"  sourcetype="csv"  | iplocation "Source IP Address"  | geostats count by Country
```
<img width="2540" height="1238" alt="image" src="https://github.com/user-attachments/assets/3cd3a7f2-4e74-4920-bead-63808480e57c" />

### What are the top 5 countries with the highest number of attacks, including their cities?

### Countries
```
source="cybersecurity_attacks.csv"  sourcetype="csv"  | iplocation "Source IP Address"  | stats count by Country | sort - count | head 5
```

<img width="2542" height="201" alt="image" src="https://github.com/user-attachments/assets/ba7b09e6-a85a-41cb-86f0-d61daefb096e" />


### Cities

```
source="cybersecurity_attacks.csv"  sourcetype="csv"  | iplocation "Source IP Address"  | stats count by City | sort - count | head 5
```


<img width="2538" height="192" alt="image" src="https://github.com/user-attachments/assets/a0efd922-5a2f-4d6c-bce3-55f0c249de83" />


### How many attacks were carried out per hour on the day with the highest number of attacks?

First we find the day with most attacks

```
source="cybersecurity_attacks.csv"  sourcetype="csv" | eval date=substr(Timestamp, 1, 11) | stats count by date | sort - count | head 1
```

<img width="213" height="66" alt="image" src="https://github.com/user-attachments/assets/9a7c12eb-49fb-4021-8872-21df3cca1523" />

Next, we analyzed the number of attacks carried out per hour

```
source="cybersecurity_attacks.csv"  sourcetype="csv" |search Timestamp=2022-03-29* | stats count by date_hour
```

<img width="2559" height="443" alt="image" src="https://github.com/user-attachments/assets/a402420a-f670-40c4-a520-0b936725dd4e" />


### Were there IP addresses that carried out attacks on different days? If so, which days?

For this question, the IP addresses had to be grouped by date, and then the ones that had a specific IP address but appeared on more than one date had to be identified.

```
source="cybersecurity_attacks.csv"  sourcetype="csv" | eval date=substr(Timestamp, 1, 11) | stats  values(date) as days  by  "Source IP Address" | eval Days_number = mvcount(days) | search Days_number > 1
```

No IP addresses were found that attacked on two different days.

However, by changing the grouping order of dates and IP addresses, it was possible to identify the IPs that launched attacks on each day.


```
source="cybersecurity_attacks.csv"  sourcetype="csv" | eval date=substr(Timestamp, 1, 11) | stats  values("Source IP Address") as Ips  by  date | eval Ip_number = mvcount(Ips)
```


<img width="2549" height="971" alt="image" src="https://github.com/user-attachments/assets/ac39a398-8403-483d-8073-d04c3f08c4fc" />

Here, you can see the number of unique IP addresses that launched attacks per day.

<img width="2542" height="532" alt="image" src="https://github.com/user-attachments/assets/2608c276-93e7-4616-95c9-b5d99f9affae" />

### Did a single IP address generate different results when launching multiple attacks?

There were no attacks from the same IP address that received different responses.

```
source="cybersecurity_attacks.csv"  sourcetype="csv" | stats values("Action Taken") as Response by "Source IP Address" | eval N_Response= mvcount(Response) | sort - N_response
```

<img width="2559" height="480" alt="image" src="https://github.com/user-attachments/assets/3187bab0-9c4f-4b0c-b782-1129e338c843" />


In the following example, as can be seen, each IP address received the same response for every attempt it launched.

```
source="cybersecurity_attacks.csv"  sourcetype="csv" | stats list("Action Taken") as Response by "Source IP Address" | eval N_Response= mvcount(Response) | sort - N_response
```

<img width="2548" height="900" alt="image" src="https://github.com/user-attachments/assets/af82f3a1-98f1-45b3-90e2-0c4558dd0cd6" />

### What were the responses to each type of attack?

In this case, the inquiry concerns the type and quantity of responses received by each type of attack carried out.

```
source="cybersecurity_attacks.csv"   | rename "Action Taken" as Response | eval N_blocked= if (Response="Blocked", 1 , 0  ), N_Ignored= if (Response="Ignored", 1 , 0  ), N_Logged= if (Response="Logged", 1 , 0  ) |   stats sum(N_blocked) as T_blocked , sum(N_Ignored) as T_ignored, sum(N_Logged) as N_logged, count as Total by "Attack Type”
```

<img width="2554" height="400" alt="image" src="https://github.com/user-attachments/assets/4485cef0-fa08-43b4-8212-e9ca70796a24" />


### What is the most common type of attack by country?


```
source="cybersecurity_attacks.csv" sourcetype="csv"  | iplocation "Source IP Address"  |top limit=1 "Attack Type" by Country
```


<img width="2530" height="734" alt="image" src="https://github.com/user-attachments/assets/c6f2a75d-f0ce-4796-a526-90d561086c4b" />





