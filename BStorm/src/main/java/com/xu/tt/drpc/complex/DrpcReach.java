package com.xu.tt.drpc.complex;

import org.apache.storm.utils.DRPCClient;

public class DrpcReach {

	public static void main(String[] args) throws Exception {
		DRPCClient client = new DRPCClient(null, "192.168.1.171", 3772);
		for (String url : new String[] { "foo.com/blog/1", "engineering.twitter.com/blog/5" }) {
			System.out.println(client.execute("reach", url));
		}
		client.close();
	}

}
