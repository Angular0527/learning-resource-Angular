using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class CamShake : MonoBehaviour {

	public GameObject ARCam;
	private float n1, n2, n3;
	private float x, y, z;

	// Use this for initialization
	void Start () {
		
	}
	
	// Update is called once per frame
	void Update () {
		Vector3 pos1 = ARCam.transform.position;
		Vector3 pos2 = ARCam.transform.eulerAngles;

		n1 = (pos1.y / (Mathf.Tan (pos2.x / 57.3f)));
		n2 = pos1.x + (n1 * Mathf.Sin (pos2.y / 57.3f));
		n3 = pos1.z + (n1 * Mathf.Cos (pos2.y / 57.3f));

		x = x + (n2 - x);
		z = z + (n3 - z);
		transform.position = new Vector3 (x, 0, z);
	}
}
